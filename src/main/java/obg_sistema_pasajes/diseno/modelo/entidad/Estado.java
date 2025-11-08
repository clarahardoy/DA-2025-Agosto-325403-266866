package obg_sistema_pasajes.diseno.modelo.entidad;

public class Estado {
    public enum NombreEstado {
        DESHABILITADO,
        SUSPENDIDO,
        PENALIZADO,
        HABILITADO;
    }
    private NombreEstado nombreEstado;

    public Estado(NombreEstado nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    public NombreEstado getNombre() {
        return nombreEstado;
    }

    public void setNombre(NombreEstado nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
