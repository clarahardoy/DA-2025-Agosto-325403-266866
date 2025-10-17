package obg_sistema_pasajes.diseno.dto;
import java.text.SimpleDateFormat;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;

public class SesionDto {
    private String fechaIngreso;
    private String usuarioCedula;
    private String nombreCompleto;

    public SesionDto(Sesion sesion) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        fechaIngreso = sdf.format(sesion.getFechaIngreso());
        usuarioCedula = sesion.getUsuario().getCedula();
        nombreCompleto = sesion.getUsuario().getNombreCompleto();
    }
    

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuarioCedula() {
        return usuarioCedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

}
