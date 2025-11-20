package obg_sistema_pasajes.diseno.modelo.entidad;

public class TipoEstado {
    
    private String nombre;

    public TipoEstado(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
