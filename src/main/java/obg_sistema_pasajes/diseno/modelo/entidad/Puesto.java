package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;

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
}
