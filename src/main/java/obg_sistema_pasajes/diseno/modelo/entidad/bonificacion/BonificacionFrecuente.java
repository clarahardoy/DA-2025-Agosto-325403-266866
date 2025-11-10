package obg_sistema_pasajes.diseno.modelo.entidad.bonificacion;

import java.util.List;
import java.util.Date;

import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

public class BonificacionFrecuente extends Bonificacion {
    
    public BonificacionFrecuente(String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        // Aplica desde el 2do tránsito del día
        return transitosHoy != null && transitosHoy.size() >= 1;
    }

    @Override
    public double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return montoTarifa * 0.5; // 50% de descuento
    }
}