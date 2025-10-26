package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.Date;


public class Transito {
    private Date fechaHora;
    private Vehiculo vehiculo;
    private Puesto puesto;
    private Propietario propietario;
    private Tarifa tarifa;
    private Bonificacion bonificacionAplicada;
    private double montoBonificado;
    private double montoPagado;


    public Transito(Vehiculo vehiculo, Puesto puesto, Propietario propietario, 
                   Tarifa tarifa, double montoFinal, Date fechaTransito) {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.propietario = propietario;
        this.tarifa = tarifa;
        this.montoPagado = montoFinal;
        this.fechaHora = fechaTransito;

            if (!propietario.getEstado().getNombre().equals("Penalizado")) { //Capaz hacer funcion en propietario para ver si esta penalizado?
            this.bonificacionAplicada = propietario.getBonificacionParaPuesto(puesto);
            if (this.bonificacionAplicada != null) {
                this.montoBonificado = tarifa.getMonto() - montoFinal;
            }
        }
        
        // Actualizar saldo del propietario
        propietario.descontarSaldo(montoFinal);
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


    public Bonificacion getBonificacionAplicada() {
        return bonificacionAplicada;
    }


    public void setBonificacionAplicada(Bonificacion bonificacionAplicada) {
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

            // Aplicar bonificación si no está penalizado
}
