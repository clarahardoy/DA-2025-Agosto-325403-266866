package obg_sistema_pasajes.diseno.modelo.entidad;

public class Vehiculo {
    private String matricula;
    private String modelo;
    private String color;
    private CategoriaVehiculo categoria;
    private Propietario propietario; // Tiene id o propitario asociado??

    public Vehiculo(String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    //---------
    public CategoriaVehiculo getCategoria() {
        return categoria;
    }

    public Propietario getPropietario() {
        return propietario; 
    }
    //---------

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategoria(CategoriaVehiculo categoria) {
        this.categoria = categoria;
    }
}