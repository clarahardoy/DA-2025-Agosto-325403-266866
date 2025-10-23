package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import obg_sistema_pasajes.diseno.modelo.entidad.Usuario;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public class SistemaAcceso {

    private List<Propietario> propietarios = new ArrayList<>();
    private List<Administrador> administradores = new ArrayList<>();
    private ArrayList<Sesion> sesiones = new ArrayList<>();

    public void agregarPropietario(String nombreCompleto, String password, String cedula,
            double saldoActual, double saldoMinimoAlerta) {
        Propietario p = new Propietario(nombreCompleto, password, cedula, saldoActual, saldoMinimoAlerta);
        propietarios.add(p);
    }

    public void agregarAdministrador(String nombreCompleto, String password, String cedula) {
        Administrador a = new Administrador(nombreCompleto, password, cedula);
        administradores.add(a);
    }

    public Sesion loginPropietario(String cedula, String pwd) throws PeajeException {
        Sesion sesion = null;
        Propietario usuario = (Propietario) login(cedula, pwd, propietarios);
        if (usuario != null) {
            sesion = new Sesion(usuario);
            sesiones.add(sesion);
            return sesion;
        }
        // TODO: cambiar cuando implementemos el estado
        if (usuario.getEstado().equals("Deshabilitado")) { 
            throw new PeajeException("Usuario deshabilitado, no puede ingresar al sistema");
        }
        return null;
    }

    public Administrador loginAdministrador(String cedula, String pwd) throws PeajeException {
        Administrador admin = (Administrador) login(cedula, pwd, administradores);
        return admin;
    }

    private Usuario login(String cedula, String pwd, List<?> lista) throws PeajeException {
        Usuario usuario;
        for (Object o : lista) {
            usuario = (obg_sistema_pasajes.diseno.modelo.entidad.Usuario) o;
            if (usuario.getCedula() != null && usuario.getCedula().equals(cedula) && usuario.getPassword().equals(pwd)) {
                return usuario;
            }
        }
        throw new PeajeException("Acceso denegado");
    }

    public ArrayList<Sesion> getSesiones() {
        return sesiones;
    }

    public void logout(Sesion s) {
        sesiones.remove(s);
    }

    public Propietario obtenerPropietarioPorCedula(String cedula) {
        for (Propietario p : propietarios) {
            if (p.getCedula() != null && p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }
   
}
