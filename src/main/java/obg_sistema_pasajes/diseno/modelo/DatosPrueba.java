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
    }
    
}
