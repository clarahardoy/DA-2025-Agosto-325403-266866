package obg_sistema_pasajes.diseno.dto;

public class AsignacionDto {
    public long fechaAsignada;
    public String nombreBonificacion;
    public String nombrePuesto;

    public AsignacionDto() {}

    public AsignacionDto(long fechaAsignada, String nombreBonificacion, String nombrePuesto) {
        this.fechaAsignada = fechaAsignada;
        this.nombreBonificacion = nombreBonificacion;
        this.nombrePuesto = nombrePuesto;
    }
}
