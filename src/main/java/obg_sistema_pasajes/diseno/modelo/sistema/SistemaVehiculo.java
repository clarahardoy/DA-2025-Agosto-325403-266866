package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

public class SistemaVehiculo {
    private List<Vehiculo> vehiculos = new ArrayList<>();


    // va ? ---------------------------------------------------
    private List<CategoriaVehiculo> categorias = new ArrayList<>();

    public void agregarCategoria(String nombre) {
        categorias.add(new CategoriaVehiculo(nombre));
    }

    public List<CategoriaVehiculo> listarCategorias() {
        return categorias;
    }
    // -------------------------------------------------------

    public void agregarVehiculoAPropietario(Propietario propietario, String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        Vehiculo v = new Vehiculo(matricula, modelo, color, categoria);
        v.setPropietario(propietario);
        propietario.getVehiculos().add(v);
        vehiculos.add(v);
    }

    public Vehiculo buscarVehiculoPorMatricula(String matricula) {
        if (matricula == null) return null;
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula() != null && v.getMatricula().equalsIgnoreCase(matricula)) return v;
        }
        return null;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculos;
    }
}
