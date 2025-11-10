package obg_sistema_pasajes.diseno.modelo.entidad.bonificacion;

import java.util.List;
import java.util.Date;

import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

public abstract class Bonificacion {
    private String nombre;

    public Bonificacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double aplicarBonificacion(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        if (puedeAplicarBonificacion(vehiculo, transitosHoy, fecha)) {
            return calcularMontoConDescuento(montoTarifa, vehiculo, transitosHoy);
        }
        return montoTarifa;
    }

    public abstract boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha);
    public abstract double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy);
    
}

