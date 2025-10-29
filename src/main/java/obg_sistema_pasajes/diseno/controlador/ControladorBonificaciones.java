package obg_sistema_pasajes.diseno.controlador;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.dto.BonificacionDto;

@RestController
@RequestMapping("/bonificaciones") 
public class ControladorBonificaciones {
    
    @PostMapping("/vista-conectada")
    public List<Respuesta> obtenerDatos() {
        // Obtener las bonificaciones y puestos definidos
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
        // Get propietario from fachada
        Propietario propietario = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietario == null) {
            throw new PeajeException("No existe el propietario");
        }
        
        // Preparar datos del propietario
        String nombre = propietario.getNombreCompleto();
        String estado = propietario.getEstado().getNombre();
        List<String> bonificacionesAsignadas = new ArrayList<>();
        for (var asignacion : propietario.getBonificacionesAsignadas()) {
            bonificacionesAsignadas.add(String.format("%s - %s", 
                asignacion.getBonificacion().getNombre(),
                asignacion.getPuesto().getNombre()));
        }
        
        return Respuesta.lista(
            new Respuesta("propietarioNombre", nombre),
            new Respuesta("propietarioEstado", estado),
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
        
        Fachada.getInstancia().asignarBonificacion(cedula, bonificacion, puesto);
        
        return Respuesta.lista(
            new Respuesta("asignacionExitosa", "La bonificación fue asignada correctamente")
        );
    }
}