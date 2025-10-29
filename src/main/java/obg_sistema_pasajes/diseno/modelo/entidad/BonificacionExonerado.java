package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.List;

public class BonificacionExonerado extends Bonificacion {
    
    public BonificacionExonerado(String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy) {
        return true; // Siempre aplica
    }

    @Override
    public double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return 0.0; // No paga nada
    }
}