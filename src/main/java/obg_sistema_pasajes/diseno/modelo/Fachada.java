package obg_sistema_pasajes.diseno.modelo;

import obg_sistema_pasajes.diseno.modelo.sistema.SistemaAcceso;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Administradores;

import java.util.ArrayList;

import obg_sistema_pasajes.diseno.exception.PeajeException;

public class Fachada {

    private SistemaAcceso sAcceso = new SistemaAcceso();


    private static Fachada instancia = new Fachada();
    
       public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }


    private Fachada() {
    }

 

    //DELEGACIONES
    public void agregarPropietario(String nombreCompleto, String password, String cedula, double saldoActual, double saldoMinimoAlerta) {
        sAcceso.agregarPropietario(nombreCompleto, password, cedula, saldoActual, saldoMinimoAlerta);
    }

    public void agregarAdministrador(String nombreCompleto, String password, String cedula) {
        sAcceso.agregarAdministrador(nombreCompleto, password, cedula);
    }

    public Sesion loginPropietario(String cedula, String pwd) throws PeajeException {
        return sAcceso.loginPropietario(cedula, pwd);
    }

    public Administradores loginAdministrador(String cedula, String pwd) throws PeajeException {
        return sAcceso.loginAdministrador(cedula, pwd);
    }

    public ArrayList<Sesion> getSesiones() {
        return sAcceso.getSesiones();
    }

    public void logout(Sesion s) {
        sAcceso.logout(s);
    }
}
