package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EstadoHabilitado extends Estado {
    public EstadoHabilitado(Propietario propietario) {
        super(TipoEstado.HABILITADO, propietario);
    }
    
    @Override
    public void habilitar() throws PeajeException {
        throw new PeajeException("El propietario ya está habilitado");
    }

    @Override
    public void deshabilitar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoDeshabilitado(getPropietario()));
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
        return true;
    }

    @Override
    public void registrarNotificacion() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = formatter.format(new Date());
        String mensaje = "[" + fechaHora + "] Se ha cambiado tu estado en el sistema. Tu estado actual es " + getNombre().toString();
        getPropietario().agregarNotificacion(mensaje);
    }
}
