package obg_sistema_pasajes.diseno.modelo.entidad.estado;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;

public class EstadoDeshabilitado extends Estado {
    
    public EstadoDeshabilitado(Propietario propietario) {
        super(propietario, "DESHABILITADO");
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
}
