package obg_sistema_pasajes.diseno.modelo.entidad;

public class Tarifa {
    private double monto;
    private CategoriaVehiculo categoria;

    public Tarifa(double monto, CategoriaVehiculo categoria) {
        this.monto = monto;
        this.categoria = categoria;
    }

    public double getMonto() {
        return monto;
    }   

    public CategoriaVehiculo getCategoria() {
        return categoria;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void setCategoria(CategoriaVehiculo categoria) {
        this.categoria = categoria;
    }
}
