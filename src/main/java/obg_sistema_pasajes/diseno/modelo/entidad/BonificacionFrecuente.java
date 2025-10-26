package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.List;

public class BonificacionFrecuente extends Bonificacion {
    
    public BonificacionFrecuente(int id, String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy) {
        // Aplica desde el 2do tránsito del día
        return transitosHoy != null && transitosHoy.size() >= 1;
    }

    @Override
    public double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return montoTarifa * 0.5; // 50% de descuento
    }
}