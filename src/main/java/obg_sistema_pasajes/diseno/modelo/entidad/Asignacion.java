package obg_sistema_pasajes.diseno.modelo.entidad;
import java.util.Date;

public class Asignacion {
    private Date fechaAsignada;
    
    public Asignacion(Date fechaAsignada) {
        this.fechaAsignada = fechaAsignada;
    }

    public Date getFechaAsignada() {
        return fechaAsignada;
    }
    
    public void setFechaAsignada(Date fechaAsignada) {
        this.fechaAsignada = fechaAsignada;
    }

}
