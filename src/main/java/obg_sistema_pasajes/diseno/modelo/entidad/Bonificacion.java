package obg_sistema_pasajes.diseno.modelo.entidad;

public abstract class Bonificacion {
    private String nombre;

    public Bonificacion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public abstract double calcularBonificacion(double monto);
    
}
