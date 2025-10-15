package obg_sistema_pasajes.diseno.modelo;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;

public class Tarifa {
    private int id;
    private double monto;
    private CategoriaVehiculo categoria;


    public Tarifa() {}

    public Tarifa(double monto, CategoriaVehiculo categoria) {
        this.monto = monto;
        this.categoria = categoria;

    }
}


