package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Administradores;

public class SistemaAcceso {

    private List<Propietario> propietarios = new ArrayList();
    private List<Administradores> administradores = new ArrayList();
    private ArrayList<Sesion> sesiones = new ArrayList();


    public void agregarPropietario(Propietario p) {
        if (p == null) return;
        propietarios.add(p);
    }

    public void agregarAdministrador(Administradores a) {
        if (a == null) return;
        administradores.add(a);
    }

    public ArrayList<Sesion> getSesiones() {
        return sesiones;
    }

    // placeholder for future login implementation
    // public Usuario login(String cedula, String password) { ... }

}
