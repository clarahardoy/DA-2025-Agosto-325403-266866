package obg_sistema_pasajes.diseno.modelo.entidad.bonificacion;

import java.util.List;

import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

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