package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import java.util.Date;
import java.util.List;

public abstract class Estado {
    private String nombre;
    private Propietario propietario;

    public Estado(Propietario propietario, String nombre) {
        this.nombre = nombre;
        this.propietario = propietario;
    }
    
    public String getNombre() {
        return nombre;
    }

    public Propietario getPropietario() {
        return propietario; 
    }


    public abstract void habilitar() throws PeajeException;
    public abstract void deshabilitar() throws PeajeException;
    public abstract void suspender() throws PeajeException;
    public abstract void penalizar() throws PeajeException; 
    

    public abstract boolean puedeLoguearse() throws PeajeException;

    public abstract Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException;

    public abstract void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException;

    public abstract double aplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha);

    public abstract void registrarNotificacion(String mensaje);

    @Override
    public String toString() {
        return nombre;
    }
}
