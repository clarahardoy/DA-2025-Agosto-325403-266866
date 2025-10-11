package obg_sistema_pasajes.diseno.modelo;

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


