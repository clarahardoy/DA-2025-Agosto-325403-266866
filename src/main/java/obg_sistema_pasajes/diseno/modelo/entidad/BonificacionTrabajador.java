package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.List;
import java.time.LocalDateTime;

public class BonificacionTrabajador extends Bonificacion {
    
    public BonificacionTrabajador(String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy) {
        //Aplica solo en días de semana (Lunes a Viernes)
        LocalDateTime ahora = LocalDateTime.now();
        int diaSemana = ahora.getDayOfWeek().getValue(); // 1=Lunes, 7=Domingo
        return diaSemana >= 1 && diaSemana <= 5;
    }

    @Override
    public double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return montoTarifa * 0.2; // 80% de descuento (paga solo 20%)
    }
}