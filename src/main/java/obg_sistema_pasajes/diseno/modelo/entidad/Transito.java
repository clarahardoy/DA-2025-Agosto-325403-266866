package obg_sistema_pasajes.diseno.modelo.entidad;
import java.util.Date;

import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;


public class Transito {
    private Date fechaHora;
    private Vehiculo vehiculo;
    private Puesto puesto;
    private Propietario propietario;
    private Tarifa tarifa;
    private Bonificacion bonificacion;
    private boolean bonificacionAplicada;
    private double montoBonificado;
    private double montoPagado;


    public Transito(Vehiculo vehiculo, Puesto puesto, Propietario propietario, 
                   Tarifa tarifa, double montoFinal, Bonificacion bonificacion, boolean bonificacionAplicada, Date fechaHora, double montoBonificado) {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.propietario = propietario;
        this.tarifa = tarifa;
        this.montoPagado = montoFinal;
        this.montoBonificado = montoBonificado;
        this.bonificacion = bonificacion;
        this.bonificacionAplicada = bonificacionAplicada;
        this.fechaHora = fechaHora; 
    }

    public Date getFechaHora() {
        return fechaHora;
    }


    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }


    public Vehiculo getVehiculo() {
        return vehiculo;
    }


    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }


    public Puesto getPuesto() {
        return puesto;
    }


    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }


    public Propietario getPropietario() {
        return propietario;
    }


    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }


    public Tarifa getTarifa() {
        return tarifa;
    }


    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }


    public Bonificacion getBonificacion() {
        return bonificacion;
    }


    public void setBonificacion(Bonificacion bonificacion) {
        this.bonificacion = bonificacion;
    }


    public boolean isBonificacionAplicada() {
        return bonificacionAplicada;
    }


    public void setBonificacionAplicada(boolean bonificacionAplicada) {
        this.bonificacionAplicada = bonificacionAplicada;
    }


    public double getMontoBonificado() {
        return  montoBonificado;
    }


    public void setMontoBonificado(double montoBonificacion) {
        this.montoBonificado = montoBonificacion;
    }


    public double getMontoPagado() {
        return montoPagado;
    }


    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public double getMontoBase() {
        return tarifa.getMonto();
    }
    
    public CategoriaVehiculo getCategoria() {
        return tarifa.getCategoria();
    }

            
}
