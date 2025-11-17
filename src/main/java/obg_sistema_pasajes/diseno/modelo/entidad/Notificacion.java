package obg_sistema_pasajes.diseno.modelo.entidad;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Notificacion {
    private Date fechaHora;
    private String mensaje;

    public Notificacion(String mensaje) {
        this.fechaHora = new Date();
        this.mensaje = mensaje;
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

    public static List<Notificacion> ordenarPorFecha(List<Notificacion> notificaciones) {
        List<Notificacion> ordenadas = new ArrayList<>(notificaciones);
        ordenadas.sort((a, b) -> {
            if (a.getFechaHora() == null && b.getFechaHora() == null) return 0;
            if (a.getFechaHora() == null) return 1;
            if (b.getFechaHora() == null) return -1;
            return b.getFechaHora().compareTo(a.getFechaHora());
        });
        return ordenadas;
    }
}
