package obg_sistema_pasajes.diseno.dto;

import obg_sistema_pasajes.diseno.modelo.entidad.Asignacion;
import java.util.Date;

public class AsignacionDto {
    public Date fechaAsignada;
    public String nombreBonificacion;
    public String nombrePuesto;

    public AsignacionDto() {}

    public AsignacionDto(Date fechaAsignada, String nombreBonificacion, String nombrePuesto) {
        this.fechaAsignada = fechaAsignada;
        this.nombreBonificacion = nombreBonificacion;
        this.nombrePuesto = nombrePuesto;
    }

    public static AsignacionDto toDto(Asignacion asignacion) {
        return new AsignacionDto(
            asignacion.getFechaAsignada(),
            asignacion.getBonificacion().getNombre(),
            asignacion.getPuesto().getNombre()
        );
    }
}
