package obg_sistema_pasajes.diseno.dto;

public class VehiculoDto {
    public String matricula;
    public String modelo;
    public String color;
    public String categoriaNombre;
    public int cantidadTransitos;
    public double montoGastado;

    public VehiculoDto() {}

    public VehiculoDto(String matricula, String modelo, String color, String categoriaNombre) {
        this(matricula, modelo, color, categoriaNombre, 0, 0.0);
    }

    public VehiculoDto(String matricula, String modelo, String color, String categoriaNombre, int cantidadTransitos, double montoGastado) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoriaNombre = categoriaNombre;
        this.cantidadTransitos = cantidadTransitos;
        this.montoGastado = montoGastado;
    }
}
