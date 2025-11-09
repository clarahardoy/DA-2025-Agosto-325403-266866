package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;

public abstract class Estado {
    public enum TipoEstado {
        DESHABILITADO,
        SUSPENDIDO,
        PENALIZADO,
        HABILITADO
    }
    private TipoEstado nombreEstado;
    private Propietario propietario;

    public Estado(TipoEstado nombreEstado, Propietario propietario) {
        this.nombreEstado = nombreEstado;
        this.propietario = propietario;
    }
    
    public TipoEstado getNombre() {
        return nombreEstado;
    }

    public Propietario getPropietario() {
        return propietario; 
    }


    public abstract void habilitar() throws PeajeException;
    public abstract void deshabilitar() throws PeajeException;
    public abstract void suspender() throws PeajeException;
    public abstract void penalizar() throws PeajeException; 
    

    public abstract boolean puedeLoguearse() throws PeajeException;

    public void registrarTransito() {

    }

    public void asignarBonificacion() {

    }

    public void aplicarDescuento() {

    }

    public abstract void registrarNotificacion();

    @Override
    public String toString() {
        return nombreEstado.name();
    }
}
