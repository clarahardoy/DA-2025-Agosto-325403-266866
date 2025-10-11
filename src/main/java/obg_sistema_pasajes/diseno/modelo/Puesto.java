package obg_sistema_pasajes.diseno.modelo;

import java.util.List;
import java.util.ArrayList;

public class Puesto {
    private int id;
    private String nombre;
    private String direccion;
    private List<Tarifa> tarifas = new ArrayList<>();


    public Puesto() {}

    public Puesto(String nombre, String direccion, List<Tarifa> tarifas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.tarifas = tarifas;
    }
}


