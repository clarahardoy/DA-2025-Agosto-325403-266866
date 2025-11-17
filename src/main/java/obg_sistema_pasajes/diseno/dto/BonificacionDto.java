package obg_sistema_pasajes.diseno.dto;

public class BonificacionDto {
    private String nombre;
    private String tipo;

    public BonificacionDto() {}

    public BonificacionDto(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}