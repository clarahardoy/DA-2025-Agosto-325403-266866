package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public class EstadoSuspendido extends Estado {

    
    public EstadoSuspendido(Propietario propietario) {
        super(propietario, "SUSPENDIDO");
    }    @Override
    public void suspender() throws PeajeException {
        throw new PeajeException("El propietario ya está suspendido");
    }

    @Override
    public void habilitar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoHabilitado(getPropietario()));
    }

    @Override
    public void deshabilitar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoDeshabilitado(getPropietario()));
    }
    
    @Override
    public void penalizar() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoPenalizado(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() throws PeajeException {
        return true;
    }
}
