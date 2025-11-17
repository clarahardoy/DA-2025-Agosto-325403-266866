package obg_sistema_pasajes.diseno.dto;

import obg_sistema_pasajes.diseno.modelo.entidad.Notificacion;

public class NotificacionDto {
    public String mensaje;

    public NotificacionDto() {}

    public NotificacionDto(String mensaje) {
        this.mensaje = mensaje;
    }

    public static NotificacionDto toDto(Notificacion notificacion) {
        return new NotificacionDto(notificacion.getMensaje());
    }
}
