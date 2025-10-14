package obg_sistema_pasajes.diseno.modelo.entidad;

public class CategoriaVehiculo {
    private String nombre;

    public CategoriaVehiculo(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
