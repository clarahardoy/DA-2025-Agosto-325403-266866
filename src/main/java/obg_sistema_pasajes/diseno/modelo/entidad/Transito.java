package obg_sistema_pasajes.diseno.modelo.entidad;
import java.util.Date;
import java.util.List;

import obg_sistema_pasajes.diseno.exception.PeajeException;
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



    public Transito(Vehiculo vehiculo, Puesto puesto, Propietario propietario, Date fechaHora) throws PeajeException {
        this(vehiculo, puesto, propietario, fechaHora, propietario.getBonificacionParaPuesto(puesto));
    }

    public Transito(Vehiculo vehiculo, Puesto puesto, Propietario propietario, Date fechaHora, Bonificacion bonificacion) throws PeajeException {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.propietario = propietario;
        this.fechaHora = fechaHora;

        // Obtener tarifa según categoría del vehículo
        this.tarifa = puesto.obtenerTarifaPorCategoria(vehiculo.getCategoria());
        double montoBase = tarifa.getMonto();

        this.montoPagado = montoBase;
        this.montoBonificado = 0.0;
        this.bonificacionAplicada = false;

        // Aplicar bonificación si corresponde
        this.bonificacion = bonificacion;
        if (this.bonificacion != null && !propietario.estaPenalizado()) {
            List<Transito> transitosDelDia = vehiculo.getTransitosDelDia(puesto, fechaHora);
            this.montoPagado = this.bonificacion.aplicarBonificacion(montoBase, vehiculo, transitosDelDia, fechaHora);
            this.bonificacionAplicada = (this.montoPagado != montoBase);
            this.montoBonificado = montoBase - this.montoPagado;
        }
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
