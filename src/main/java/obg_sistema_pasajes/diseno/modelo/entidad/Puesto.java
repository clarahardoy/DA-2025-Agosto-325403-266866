package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;

import obg_sistema_pasajes.diseno.exception.PeajeException;

public class Puesto {
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifas = new ArrayList<>();

    public Puesto(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void agregarTarifa(Tarifa t) {
        tarifas.add(t);
    }

    public Tarifa obtenerTarifaPorCategoria(CategoriaVehiculo categoria) throws PeajeException {
        for (Tarifa t : tarifas) {
            if (t.getCategoria().getNombreCategoria() == categoria.getNombreCategoria()) {
                return t;
            }
        }
        throw new PeajeException("No existe tarifa para la categoría: " + categoria.getNombreCategoria().toString());
    }
}
