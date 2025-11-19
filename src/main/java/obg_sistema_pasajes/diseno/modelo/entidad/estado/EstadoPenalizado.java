package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import java.util.Date;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;

public class EstadoPenalizado extends Estado {

    
    public EstadoPenalizado(Propietario propietario) {
        super(propietario, "PENALIZADO");
    }    @Override
    public void penalizar() throws PeajeException {
        throw new PeajeException("El propietario ya está penalizado");
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
    public void suspender() throws PeajeException {
        getPropietario().cambiarEstado(new EstadoSuspendido(getPropietario()));
    }

    @Override
    public boolean puedeLoguearse() throws PeajeException {
        return false;
    }

    @Override
    public void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        throw new PeajeException("El propietario está penalizado, no puede recibir bonificaciones");
    }

    @Override
    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        return getPropietario().hacerRegistrarTransito(vehiculo, puesto, fechaHora);
    }

    @Override
    public double aplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        //  penalizados no reciben descuentos aunq tengan bonificaciones asignadas, pagan el 100% igual
        return montoTarifa;
    }

    @Override
    public void registrarNotificacion(String mensaje) {
        //  propietarios penalizados no reciben notificaciones
    }
}
