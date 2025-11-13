package obg_sistema_pasajes.diseno.modelo.entidad.estado;

import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;

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

    public void registrarTransito() {

    }

    public void asignarBonificacion() {

    }

    public void aplicarDescuento() {

    }

    public void registrarNotificacion() {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = formatter.format(new java.util.Date());
        String mensaje = "[" + fechaHora + "] Se ha cambiado tu estado en el sistema. Tu estado actual es " + getNombre();
        getPropietario().agregarNotificacion(mensaje);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
