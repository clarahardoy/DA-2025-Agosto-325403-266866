package obg_sistema_pasajes.diseno.dto;

public class VehiculoDto {
    public String matricula;
    public String modelo;
    public String color;
    public String categoriaNombre;

    public VehiculoDto() {}

    public VehiculoDto(String matricula, String modelo, String color, String categoriaNombre) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoriaNombre = categoriaNombre;
    }
}
