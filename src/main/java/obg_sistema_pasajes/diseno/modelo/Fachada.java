package obg_sistema_pasajes.diseno.modelo;

import obg_sistema_pasajes.diseno.modelo.sistema.SistemaAcceso;
import obg_sistema_pasajes.diseno.modelo.sistema.SistemaPuestos;
import obg_sistema_pasajes.diseno.modelo.sistema.SistemaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Tarifa;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;

import java.util.ArrayList;

import obg_sistema_pasajes.diseno.exception.PeajeException;

public class Fachada {

    private SistemaAcceso sAcceso = new SistemaAcceso();
    private SistemaPuestos SPuesto = new SistemaPuestos();
    private SistemaVehiculo SVehiculo = new SistemaVehiculo();


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

    public Administrador loginAdministrador(String cedula, String pwd) throws PeajeException {
        return sAcceso.loginAdministrador(cedula, pwd);
    }

    public ArrayList<Sesion> getSesiones() {
        return sAcceso.getSesiones();
    }

    public void logout(Sesion s) {
        sAcceso.logout(s);
    }


    //-----------------PUESTOS-----------------
    public void agregarPuesto(String nombre, String direccion) {
        SPuesto.agregarPuesto(nombre, direccion);
    }

    public java.util.List<Puesto> listarPuestos() {
        return SPuesto.listarPuestos();
    }

    public java.util.List<Tarifa> obtenerTarifasPuesto(String nombrePuesto) {
        return SPuesto.obtenerTarifasPuesto(nombrePuesto);
    }



    //-----------------VEHICULOS-----------------
    public void agregarVehiculoAPropietario(String cedula, String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        Propietario p = sAcceso.obtenerPropietarioPorCedula(cedula);
        if (p != null) SVehiculo.agregarVehiculoAPropietario(p, matricula, modelo, color, categoria);
    }
}
