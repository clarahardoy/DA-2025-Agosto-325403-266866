package obg_sistema_pasajes.diseno.modelo.entidad;

import java.text.SimpleDateFormat;
import java.util.Date;

import obg_sistema_pasajes.diseno.dto.NotificacionDto;

public class Notificacion {
    private Date fechaHora;
    private String mensaje;

    public Notificacion(String mensaje) {
        this.fechaHora = new Date();
        this.mensaje = mensaje;
    }

    public NotificacionDto toDto() {
        return new NotificacionDto(this.getMensaje());
    }
    
    public Date getFechaHora() { return fechaHora; }
    public String getMensaje() { return mensaje; }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return "[" + formatter.format(fechaHora) + "] " + mensaje;
    }
}
