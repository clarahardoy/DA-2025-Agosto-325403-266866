package obg_sistema_pasajes.diseno.modelo;

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
    
    // precarga bonificaciones (agregar las 3 al sistema por nombre)
    fachada.agregarBonificacion("Bonificación trabajador");
    fachada.agregarBonificacion("Bonificación frecuente");
    fachada.agregarBonificacion("Bonificación exonerado");
    }
    
}
