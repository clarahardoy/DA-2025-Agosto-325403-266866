package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import java.util.Date;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;

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

    @Override
    public void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        throw new PeajeException("El propietario está deshabilitado, no puede recibir bonificaciones");
    }

    @Override
    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
       throw new PeajeException("El propietario está deshabilitado, no puede realizar tránsitos");
    }

    @Override
    public double aplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return getPropietario().hacerAplicarDescuentoPorBonificacionesAsignadas(bonificacion, montoTarifa, vehiculo, transitosHoy);
    }

    @Override
    public void registrarNotificacion(String mensaje) {
        //  propietarios deshabilitados no reciben notificaciones
    }
}
