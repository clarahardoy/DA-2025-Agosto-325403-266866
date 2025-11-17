package obg_sistema_pasajes.diseno.modelo.entidad;
import java.util.Date;
import java.util.List;
import java.util.Comparator;

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

    public Transito(Vehiculo vehiculo, Puesto puesto, Propietario propietario, 
                Date fechaHora, Bonificacion bonificacion) throws PeajeException {
        this.vehiculo = vehiculo;
        this.puesto = puesto;
        this.propietario = propietario;
        this.fechaHora = fechaHora;
        this.bonificacion = bonificacion;
        this.tarifa = puesto.obtenerTarifaPorCategoria(vehiculo.getCategoria());
        this.montoPagado = calcularMontoPagado(); 
    }

    private double calcularMontoPagado() {
        double montoBase = tarifa.getMonto();
        
        if (bonificacion == null || propietario.estaPenalizado()) {
            this.bonificacionAplicada = false;
            this.montoBonificado = 0.0;
            return montoBase;
        }
    
        List<Transito> transitosDelDia = vehiculo.getTransitosDelDia(puesto, fechaHora);
        double montoConDescuento = bonificacion.aplicarBonificacion(
            montoBase, vehiculo, transitosDelDia, fechaHora
        );
        
        this.bonificacionAplicada = (montoConDescuento != montoBase);
        this.montoBonificado = montoBase - montoConDescuento;
        
        return montoConDescuento;
}

    public Date getFechaHora() {
        return fechaHora;
    }


    public Vehiculo getVehiculo() {
        return vehiculo;
    }


    public Puesto getPuesto() {
        return puesto;
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


    public Bonificacion getBonificacion() {
        return bonificacion;
    }


    public boolean isBonificacionAplicada() {
        return bonificacionAplicada;
    }


    public double getMontoBonificado() {
        return  montoBonificado;
    }


    public double getMontoPagado() {
        return montoPagado;
    }

    public double getMontoBase() {
        return tarifa.getMonto();
    }
    
    public CategoriaVehiculo getCategoria() {
        return tarifa.getCategoria();
    }

    public static Comparator<Transito> porFechaDescendente() {
        return (transito1, transito2) -> {
            if (transito1.getFechaHora() == null && transito2.getFechaHora() == null) return 0;
            if (transito1.getFechaHora() == null) return 1;
            if (transito2.getFechaHora() == null) return -1;
            return transito2.getFechaHora().compareTo(transito1.getFechaHora());
        };
    }
            
}
