package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import java.util.Date;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;

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

    @Override
    public void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        getPropietario().hacerAsignarBonificacion(bonificacion, puesto);
    }

    @Override
    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        throw new PeajeException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
    }

    @Override
    public double aplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        return getPropietario().hacerAplicarDescuentoPorBonificacionesAsignadas(bonificacion, montoTarifa, vehiculo, transitosHoy, fecha);
    }

    @Override
    public void registrarNotificacion(String mensaje) {
        getPropietario().hacerRegistrarNotificacion(mensaje);
    }
}