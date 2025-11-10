package obg_sistema_pasajes.diseno.dto;

public class TransitoDto {
    public String propietario;
    public String estadoPropietario;
    public String puestoNombre;
    public String matricula;
    public String categoria;
    public String bonificacion;
    public double montoTarifa;
    public double montoBonificacion;
    public double costoTransito;
    public double saldoFinal;
    public String fechaHora;

    public TransitoDto() {
    }

    // Constructor compacto (compatibilidad con controlador transito)
    public TransitoDto(String propietario, String estadoPropietario, String categoria, 
                                String bonificacion, double costoTransito, double saldoFinal) {
        this.propietario = propietario;
        this.estadoPropietario = estadoPropietario;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.costoTransito = costoTransito;
        this.saldoFinal = saldoFinal;
    }

    // Constructor completo para la vista del propietario
    public TransitoDto(String puestoNombre, String matricula, String categoria,
            String bonificacion, double montoTarifa, double montoBonificacion,
            double montoPagado, String fechaHora) {
        this.puestoNombre = puestoNombre;
        this.matricula = matricula;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.montoTarifa = montoTarifa;
        this.montoBonificacion = montoBonificacion;
        this.costoTransito = montoPagado;
        this.saldoFinal = 0; 
        this.fechaHora = fechaHora;
    }
}

