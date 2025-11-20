package obg_sistema_pasajes.diseno.modelo.entidad;
import java.util.Date;

public class Asignacion {
    private Date fechaAsignada;
    //private Propietario propietario;
    private Bonificacion bonificacion;
    private Puesto puesto;
    
    public Asignacion(Bonificacion bonificacion, Puesto puesto) {
        this.bonificacion = bonificacion;
        this.puesto = puesto;
        this.fechaAsignada = new Date(); // Fecha actual
    }
    public Date getFechaAsignada() {
        return fechaAsignada;
    }
    
    public void setFechaAsignada(Date fechaAsignada) {
        this.fechaAsignada = fechaAsignada;
    }

    public Bonificacion getBonificacion() {
        return bonificacion;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public String getDescripcion() {
        return bonificacion.getNombre() + " - " + puesto.getNombre();
    }

}
