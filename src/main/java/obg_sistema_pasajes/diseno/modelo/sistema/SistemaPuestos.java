package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Tarifa;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;

public class SistemaPuestos {
    private List<Puesto> puestos = new ArrayList<>();

    public void agregarPuesto(String nombre, String direccion) {
        puestos.add(new Puesto(nombre, direccion));
    }

    public List<Puesto> listarPuestos() {
        return puestos;
    }

    public void agregarTarifaAPuesto(String nombrePuesto, double monto, CategoriaVehiculo categoria) {
        for (Puesto p : puestos) {
            if (p.getNombre().equals(nombrePuesto)) {
                p.agregarTarifa(new Tarifa(monto, categoria));
                return;
            }
        }
    }

    public List<Tarifa> obtenerTarifasPuesto(String nombrePuesto) {
        for (Puesto p : puestos) {
            if (p.getNombre().equals(nombrePuesto)) return p.getTarifas();
        }
        return new ArrayList<>();
    }

    public Puesto obtenerPuestoPorNombre(String nombrePuesto) {
        for (Puesto p : puestos) {
            if (p.getNombre().equals(nombrePuesto)) return p;
        }
        return null;
    }
}
