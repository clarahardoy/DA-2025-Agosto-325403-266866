package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

import java.text.SimpleDateFormat;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.dto.TransitoDto;

@RestController
@RequestMapping("/transito")
public class ControladorTransito {

    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista() {
        return Respuesta.lista(
            new Respuesta("vistaConectada", "Vista conectada correctamente"),
            new Respuesta("puestos", Fachada.getInstancia().listarPuestos())
        );
    }

    @PostMapping("/emular")
    public List<Respuesta> emularTransito(
            @RequestParam String puestoNombre,
            @RequestParam String matricula,
            @RequestParam String fechaHora
            ) {
        
        try {
            Fachada fachada = Fachada.getInstancia();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date fechaHoraTransito = dateFormat.parse(fechaHora);
            
            Vehiculo vehiculo = fachada.obtenerVehiculoPorMatricula(matricula);
            if (vehiculo == null) {  
                throw new PeajeException("No existe el vehículo");
            }
            
            Puesto puesto = fachada.obtenerPuestoPorNombre(puestoNombre);
            Transito transito = fachada.registrarTransito(vehiculo, puesto, fechaHoraTransito);
            
            TransitoDto transitoDto = construirTransitoDto(transito);
            
            return Respuesta.lista(
                new Respuesta("resultadoTransito", transitoDto)
            );
            
        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("error", e.getMessage())
            );
        } catch (Exception e) {
            return Respuesta.lista(
                new Respuesta("error", "Error inesperado: " + e.getMessage())
            );
        }
    }

    private TransitoDto construirTransitoDto(Transito transito) {
        return new TransitoDto(
            transito.getPropietario().getNombreCompleto(),
            transito.getPropietario().getEstado().getNombre().toString(),
            transito.getCategoria().getNombreCategoria().toString(),
            transito.getBonificacion() != null ? 
                transito.getBonificacion().getNombre() : "Ninguna",
            transito.getMontoPagado(),
            transito.getPropietario().getSaldoActual()
        );
    }
}
