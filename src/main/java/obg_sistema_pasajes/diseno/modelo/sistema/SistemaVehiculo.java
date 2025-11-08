package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo.NombreCategoria;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public class SistemaVehiculo {
    private List<Vehiculo> vehiculos = new ArrayList<>();


    // va ? ---------------------------------------------------
    private List<CategoriaVehiculo> categorias = new ArrayList<>();

    public void agregarCategoria(NombreCategoria nombre) {
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

    public Vehiculo obtenerVehiculoPorMatricula(String matricula) throws PeajeException {
        if (matricula == null) return null;
        Vehiculo vehiculo = null;
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula() != null && v.getMatricula().equalsIgnoreCase(matricula)) {
                vehiculo = v;
                break;
            }
        }
        if (vehiculo == null) {
            throw new PeajeException("No existe el vehículo");
        }
        return vehiculo;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculos;
    }
}
