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

        List<Respuesta> respuestas = new ArrayList<>(buildInicialData());

        if (propietarioActual != null) {
            propietarioActual.agregarObservador(this);
            
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
            @RequestParam String bonificacion,
            @RequestParam String puesto) throws PeajeException {
        
        if (propietarioActual == null) {
            throw new PeajeException("Debe buscar al propietario primero");
        }
        
        if (bonificacion == null || bonificacion.trim().isEmpty()) {
            throw new PeajeException("Debe especificar una bonificación");
        }
        
        if (puesto == null || puesto.trim().isEmpty()) {
            throw new PeajeException("Debe especificar un puesto");
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
        if(propietarioActual!=null) propietarioActual.quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento != null && evento.equals(Propietario.Eventos.CAMBIO_BONIFICACIONES)) {
            if (propietarioActual != null) {
                conexionNavegador.enviarJSON(Respuesta.lista(bonificacionesAsignadas()));
            }
        }
    }

    private Respuesta bonificacionesAsignadas() {
        return new Respuesta("bonificacionesAsignadas", propietarioActual.getDescripcionesBonificaciones());
    }

    
}