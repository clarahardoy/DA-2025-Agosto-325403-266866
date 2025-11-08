package obg_sistema_pasajes.diseno.dto;

public class TransitoDto {
    public String propietario;
    public String estadoPropietario;
    public String categoria;
    public String bonificacion;
    public double costoTransito;
    public double saldoFinal;

    public TransitoDto() {
    }

    public TransitoDto(String propietario, String estadoPropietario, String categoria, 
                                String bonificacion, double costoTransito, double saldoFinal) {
        this.propietario = propietario;
        this.estadoPropietario = estadoPropietario;
        this.categoria = categoria;
        this.bonificacion = bonificacion;
        this.costoTransito = costoTransito;
        this.saldoFinal = saldoFinal;
    }
}

