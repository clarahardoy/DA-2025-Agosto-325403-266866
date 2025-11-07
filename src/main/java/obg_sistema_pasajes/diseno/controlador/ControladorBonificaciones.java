package obg_sistema_pasajes.diseno.controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.beans.factory.annotation.Autowired;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Asignacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Bonificacion;
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
    private final ConexionNavegador conexionNavegador;

    public ControladorBonificaciones(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> obtenerDatos(@SessionAttribute(name = "usuarioAdmin") Administrador admin) {
        if (administradorSesion != null) administradorSesion.quitarObservador(this);
        administradorSesion = admin;
        administradorSesion.agregarObservador(this);

        return buildInicialData();
    }

    private List<Respuesta> buildInicialData(){
        List<Bonificacion> bonificaciones = Fachada.getInstancia().listarBonificaciones();
        List<Puesto> puestos = Fachada.getInstancia().listarPuestos();
        
        List<String> nombresBonificaciones = new ArrayList<>();
        for (Bonificacion b : bonificaciones) {
            nombresBonificaciones.add(b.getNombre());
        }

        List<String> nombresPuestos = new ArrayList<>();
        for (Puesto p : puestos) {
            nombresPuestos.add(p.getNombre());
        }
        
        return Respuesta.lista(
            new Respuesta("bonificacionesDefinidas", nombresBonificaciones),
            new Respuesta("puestosDefinidos", nombresPuestos)
        );
    }
    
    @PostMapping("/buscar-propietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        Propietario propietario = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe el propietario");
        }
        
        List<String> bonificacionesAsignadas = new ArrayList<>();
        for (Asignacion asignacion : propietario.getBonificacionesAsignadas()) {
            bonificacionesAsignadas.add(asignacion.getBonificacion().getNombre() + " - " + asignacion.getPuesto().getNombre());
        }
        
        return Respuesta.lista(
            new Respuesta("propietarioNombre", propietario.getNombreCompleto()),
            new Respuesta("propietarioEstado", propietario.getEstado().getNombre()),
            new Respuesta("bonificacionesAsignadas", bonificacionesAsignadas)
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
        
        Propietario propietario = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietario == null) throw new PeajeException("No existe el propietario");

        Puesto puestoObj = Fachada.getInstancia().obtenerPuestoPorNombre(puesto);
        if (puestoObj == null) throw new PeajeException("Debe especificar un puesto");

        // Delegar la asignación pasando objetos resueltos
        Fachada.getInstancia().asignarBonificacion(propietario, bonificacion, puestoObj);
        
        return Respuesta.lista(
            new Respuesta("asignacionExitosa", "La bonificación fue asignada correctamente")
        );
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(administradorSesion!=null) administradorSesion.quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        // En cualquier evento que llegue, reenviamos el estado inicial actualizado al navegador
        conexionNavegador.enviarJSON(buildInicialData());
    }

    
}