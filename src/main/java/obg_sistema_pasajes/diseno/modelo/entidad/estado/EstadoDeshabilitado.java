package obg_sistema_pasajes.diseno.modelo.entidad.estado;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EstadoDeshabilitado extends Estado {
    
    public EstadoDeshabilitado(Propietario propietario) {
        super(TipoEstado.DESHABILITADO, propietario);
    }

    @Override
    public void deshabilitar() throws PeajeException {
        throw new PeajeException("El propietario ya está deshabilitado");
    }

    @Override
    public void habilitar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoHabilitado(getPropietario()));
        
    }

    @Override
    public void suspender() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoSuspendido(getPropietario()));
    }

    @Override
    public void penalizar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoPenalizado(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() throws PeajeException {
        return false;
    }

    @Override
    public void registrarNotificacion() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = formatter.format(new Date());
        String mensaje = "[" + fechaHora + "] Se ha cambiado tu estado en el sistema. Tu estado actual es " + getNombre().toString();
        getPropietario().agregarNotificacion(mensaje);
    }
}
