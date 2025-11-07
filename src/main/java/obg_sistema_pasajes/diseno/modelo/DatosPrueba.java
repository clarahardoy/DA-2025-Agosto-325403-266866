package obg_sistema_pasajes.diseno.modelo;

import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo.NombreCategoria;

public class DatosPrueba {

    public static void cargar() {

    Fachada fachada = Fachada.getInstancia();

    // precarga propietarios
    fachada.agregarPropietario("Usuario Propietario", "prop.123", "23456789", 2000, 500);
    fachada.agregarPropietario("Clara Hardoy", "test123", "52252441", 1000, 500);

    // precarga administradores
    fachada.agregarAdministrador("Usuario Administrador", "admin.123", "12345678");
    fachada.agregarAdministrador("Mariano Rama", "test123", "52242443");

    // precarga puestos
    fachada.agregarPuesto("Puesto 204 - Ruta Interbalnearia", "Km 204 Ruta IB");
    fachada.agregarPuesto("Puesto 156 - Ruta 1", "Km 156 Ruta 1");
    fachada.agregarPuesto("Puesto 73 - Ruta 5", "Km 73 Ruta 5");

    // precarga tarifas
        // Puesto 204 - Ruta Interbalnearia
    fachada.agregarTarifaAPuesto("Puesto 204 - Ruta Interbalnearia", 500, new CategoriaVehiculo(NombreCategoria.AUTO));
    fachada.agregarTarifaAPuesto("Puesto 204 - Ruta Interbalnearia", 500, new CategoriaVehiculo(NombreCategoria.CAMIONETA));
    fachada.agregarTarifaAPuesto("Puesto 204 - Ruta Interbalnearia", 500, new CategoriaVehiculo(NombreCategoria.CAMION));
    
    // Puesto 156 - Ruta 1
    fachada.agregarTarifaAPuesto("Puesto 156 - Ruta 1", 1000, new CategoriaVehiculo(NombreCategoria.AUTO));
    fachada.agregarTarifaAPuesto("Puesto 156 - Ruta 1", 1000, new CategoriaVehiculo(NombreCategoria.CAMIONETA));
    fachada.agregarTarifaAPuesto("Puesto 156 - Ruta 1", 1000, new CategoriaVehiculo(NombreCategoria.CAMION));
    
    // Puesto 73 - Ruta 5
    fachada.agregarTarifaAPuesto("Puesto 73 - Ruta 5", 1500, new CategoriaVehiculo(NombreCategoria.AUTO));
    fachada.agregarTarifaAPuesto("Puesto 73 - Ruta 5", 150, new CategoriaVehiculo(NombreCategoria.CAMIONETA));
    fachada.agregarTarifaAPuesto("Puesto 73 - Ruta 5", 150, new CategoriaVehiculo(NombreCategoria.CAMION));

    // precarga vehículos
    fachada.agregarVehiculoAPropietario("52252441", "ABC1234", "Toyota Corolla", "Blanco", new CategoriaVehiculo(NombreCategoria.AUTO));
    fachada.agregarVehiculoAPropietario("23456789", "XYZ5678", "Ford Ranger", "Negro", new CategoriaVehiculo(NombreCategoria.CAMIONETA));
    fachada.agregarVehiculoAPropietario("23456789", "DEF9012", "Honda Civic", "Rojo", new CategoriaVehiculo(NombreCategoria.AUTO));
    fachada.agregarVehiculoAPropietario("52252441", "CHA1234", "Camioneta Nexxus", "Negro", new CategoriaVehiculo(NombreCategoria.CAMIONETA));

    // precarga bonificaciones (agregar las 3 al sistema por nombre)
    fachada.agregarBonificacion("Bonificación trabajador");
    fachada.agregarBonificacion("Bonificación frecuente");
    fachada.agregarBonificacion("Bonificación exonerado");
    }
    
}
