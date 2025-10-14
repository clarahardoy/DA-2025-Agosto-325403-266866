package obg_sistema_pasajes.diseno.modelo.entidad;

public class Puesto {
    private int nombre;
    private int direccion;

    public Puesto(int nombre, int direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getNombre() {
        return nombre;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}
