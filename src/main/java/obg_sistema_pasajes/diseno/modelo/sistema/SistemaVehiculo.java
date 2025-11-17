package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

public class SistemaVehiculo {
    private List<Vehiculo> vehiculos = new ArrayList<>();


    public void agregarVehiculoAPropietario(Propietario propietario, String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        Vehiculo v = new Vehiculo(matricula, modelo, color, categoria);
        v.setPropietario(propietario);
        propietario.agregarVehiculo(v);
        vehiculos.add(v);
    }

    public Vehiculo obtenerVehiculoPorMatricula(String matricula) {
        Vehiculo vehiculo = null;
        for (Vehiculo v : vehiculos) {
            if (v.getMatricula() != null && v.getMatricula().equalsIgnoreCase(matricula)) {
                vehiculo = v;
                break;
            }
        }
        return vehiculo;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculos;
    }
}
