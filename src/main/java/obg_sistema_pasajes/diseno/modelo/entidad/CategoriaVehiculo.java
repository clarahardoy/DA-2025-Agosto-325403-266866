package obg_sistema_pasajes.diseno.modelo.entidad;

public class CategoriaVehiculo {
    public enum NombreCategoria {
        AUTO,
        CAMIONETA,
        CAMION
    }    
    private NombreCategoria nombreCategoria;

    public CategoriaVehiculo(NombreCategoria nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
    
    public NombreCategoria getNombreCategoria() {
        return nombreCategoria;
    }


    public void setNombre(NombreCategoria nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    @Override
    public String toString() {
        return nombreCategoria.name();
    }
}
