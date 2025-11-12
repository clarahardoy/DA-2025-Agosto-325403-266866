package obg_sistema_pasajes.diseno.dto;

public class TransitoDto {
    // Campos para emulación de tránsito (admin)
    public String propietario;
    public String estadoPropietario;
    public Double saldoFinal;  // Solo para admin, null en vista propietario
    
    // Campos para tablero propietario
    public String puestoNombre;
    public String matricula;
    public String categoria;
    public String bonificacion;
    public Double montoTarifa;
    public Double montoBonificado;
    public Double montoPagado;
    public String fecha;
    public String hora;

    public TransitoDto() {
    }

    // Constructor compacto (compatibilidad con controlador transito)
    public TransitoDto(String propietario, String estadoPropietario, String categoria, 
                                String bonificacion, double montoPagado, double saldoFinal) {
        this.propietario = propietario;
        this.estadoPropietario = estadoPropietario;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.montoPagado = montoPagado;
        this.saldoFinal = saldoFinal;
    }

    // Constructor completo para la vista del propietario
    public TransitoDto(String puestoNombre, String matricula, String categoria,
            String bonificacion, double montoTarifa, double montoBonificado,
            double montoPagado, String fecha, String hora) {
        this.puestoNombre = puestoNombre;
        this.matricula = matricula;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.montoTarifa = montoTarifa;
        this.montoBonificado = montoBonificado;
        this.montoPagado = montoPagado;
        // No inicializar campos de admin (saldoFinal, propietario, estadoPropietario)
        this.fecha = fecha;
        this.hora = hora;
    }
}

