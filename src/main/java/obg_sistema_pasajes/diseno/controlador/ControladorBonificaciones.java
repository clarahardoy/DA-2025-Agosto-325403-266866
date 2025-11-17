package obg_sistema_pasajes.diseno.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.ConexionNavegador;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import observador.Observable;
import observador.Observador;


@RestController
@RequestMapping("/bonificaciones")
@Scope ("session")

public class ControladorBonificaciones implements Observador {

    private Administrador administradorSesion;
    private Propietario propietarioActual;
    private final ConexionNavegador conexionNavegador;

    public ControladorBonificaciones(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(HttpSession sesion) throws PeajeException {
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdmin");
        if (admin == null) {
            return Respuesta.lista(new Respuesta("paginaLogin", "/login/administrador.html"));
        }

        if (administradorSesion != null) administradorSesion.quitarObservador(this);
        administradorSesion = admin;
        administradorSesion.agregarObservador(this);




        List<Respuesta> respuestas = new ArrayList<>(buildInicialData());

        if (propietarioActual != null) {
            respuestas.add(new Respuesta("propietarioNombre", propietarioActual.getNombreCompleto()));
            respuestas.add(new Respuesta("propietarioEstado", propietarioActual.getEstado().getNombre()));
            respuestas.add(new Respuesta("bonificacionesAsignadas", propietarioActual.getDescripcionesBonificaciones()));
            respuestas.add(new Respuesta("cedulaPropietario", propietarioActual.getCedula()));
        }

        return respuestas;
    }

    private List<Respuesta> buildInicialData(){
        return Respuesta.lista(
            new Respuesta("bonificacionesDefinidas", Fachada.getInstancia().getNombresBonificaciones()),
            new Respuesta("puestosDefinidos", Fachada.getInstancia().getNombresPuestos())
        );
    }
    
    @PostMapping("/buscar-propietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        Propietario propietario = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe el propietario");
        }

        // Quitar observador del propietario anterior si existe
        if (propietarioActual != null && !propietarioActual.equals(propietario)) {
            propietarioActual.quitarObservador(this);
        }

        // Registrar como observador del nuevo propietario
        propietarioActual = propietario;
        propietarioActual.agregarObservador(this);

        return Respuesta.lista(
            new Respuesta("propietarioNombre", propietario.getNombreCompleto()),
            new Respuesta("propietarioEstado", propietario.getEstado().getNombre()),
            new Respuesta("bonificacionesAsignadas", propietario.getDescripcionesBonificaciones())
        );
    }
    
    @PostMapping("/asignar")
    public List<Respuesta> asignarBonificacion(
            @RequestParam String cedula,
            @RequestParam String bonificacion,
            @RequestParam String puesto) throws PeajeException {
        
        if (bonificacion == null || bonificacion.trim().isEmpty()) {
            throw new PeajeException("Debe especificar una bonificación");
        }
        
        if (puesto == null || puesto.trim().isEmpty()) {
            throw new PeajeException("Debe especificar un puesto");
        }
        
        // IMPORTANTE: Usar propietarioActual en lugar de buscar de nuevo
        if (propietarioActual == null || !propietarioActual.getCedula().equals(cedula)) {
            throw new PeajeException("Debe buscar al propietario primero");
        }

        Puesto puestoObj = Fachada.getInstancia().obtenerPuestoPorNombre(puesto);
        if (puestoObj == null) throw new PeajeException("Debe especificar un puesto");

        // Delegar la asignación usando propietarioActual (el que está siendo observado)
        Fachada.getInstancia().asignarBonificacion(propietarioActual, bonificacion, puestoObj);
        
        return Respuesta.lista(
            new Respuesta("asignacionExitosa", "La bonificación fue asignada correctamente")
        );
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(administradorSesion!=null) administradorSesion.quitarObservador(this);
        if(propietarioActual!=null) propietarioActual.quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        
        if (origen instanceof Administrador) {
            conexionNavegador.enviarJSON(buildInicialData());
        } else if (origen instanceof Propietario && propietarioActual != null && origen.equals(propietarioActual)) {
            if (evento.equals(Propietario.Eventos.CAMBIO_BONIFICACIONES)) {
                conexionNavegador.enviarJSON(buildBonificacionesAsignadas());
            }
        } 
    }

    private List<Respuesta> buildBonificacionesAsignadas() {
        if (propietarioActual == null) return new ArrayList<>();

        return Respuesta.lista(
            new Respuesta("bonificacionesAsignadas", propietarioActual.getDescripcionesBonificaciones())
        );
    }

    
}