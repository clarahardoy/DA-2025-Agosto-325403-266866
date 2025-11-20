package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

public class BonificacionTrabajador extends Bonificacion {
    
    public BonificacionTrabajador(String nombre) {
        super(nombre);
    }

    @Override
    public boolean puedeAplicarBonificacion(Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        // Aplica solo en días de semana (Lunes a Viernes) 
        if (fecha == null) {
            return false;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK); 
        return diaSemana >= Calendar.MONDAY && diaSemana <= Calendar.FRIDAY;
    }

    @Override
    public double calcularMontoConDescuento(double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy) {
        return montoTarifa * 0.2; // 80% de descuento (paga solo 20%)
    }
}