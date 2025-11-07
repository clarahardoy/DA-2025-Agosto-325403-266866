package obg_sistema_pasajes.diseno.modelo;

import obg_sistema_pasajes.diseno.modelo.sistema.SistemaAcceso;
import obg_sistema_pasajes.diseno.modelo.sistema.SistemaPuestos;
import obg_sistema_pasajes.diseno.modelo.sistema.SistemaVehiculo;
import obg_sistema_pasajes.diseno.modelo.sistema.SistemaBonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Tarifa;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import obg_sistema_pasajes.diseno.modelo.entidad.CategoriaVehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import obg_sistema_pasajes.diseno.exception.PeajeException;
// Fachada extends Observable
public class Fachada {

    private SistemaAcceso sAcceso = new SistemaAcceso();
    private SistemaPuestos SPuesto = new SistemaPuestos();
    private SistemaVehiculo SVehiculo = new SistemaVehiculo();
    private SistemaBonificacion SBonificacion = new SistemaBonificacion();

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

    public List<Puesto> listarPuestos() {
        return SPuesto.listarPuestos();
    }

    public List<Tarifa> obtenerTarifasPuesto(String nombrePuesto) {
        return SPuesto.obtenerTarifasPuesto(nombrePuesto);
    }
    
    public Puesto obtenerPuestoPorNombre(String nombrePuesto) {
        return SPuesto.obtenerPuestoPorNombre(nombrePuesto);
    }

    public void agregarTarifaAPuesto(String nombrePuesto, double monto, CategoriaVehiculo nombreCategoria) {
        SPuesto.agregarTarifaAPuesto(nombrePuesto, monto, nombreCategoria);
    }


    //-----------------VEHICULOS-----------------
    public void agregarVehiculoAPropietario(String cedula, String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        Propietario p = obtenerPropietarioPorCedula(cedula);
        if (p != null) SVehiculo.agregarVehiculoAPropietario(p, matricula, modelo, color, categoria);
    }

    public Vehiculo obtenerVehiculoPorMatricula(String matricula) throws PeajeException {
        return SVehiculo.obtenerVehiculoPorMatricula(matricula);
    }
    //-----------------PROPIETARIOS-----------------
    public Propietario obtenerPropietarioPorCedula(String cedula) {
        return sAcceso.obtenerPropietarioPorCedula(cedula);
    }

    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        Propietario propietario = vehiculo.getPropietario();
        if (propietario == null) {
            throw new PeajeException("El vehículo no tiene propietario asignado");
        }
        return propietario.registrarTransito(vehiculo, puesto, fechaHora);
    }

    //-----------------BONIFICACIONES-----------------

    public List<Bonificacion> listarBonificaciones() {
        return SBonificacion.listarBonificaciones();
    }

    public Bonificacion obtenerBonificacionPorNombre(String nombre) {
        return SBonificacion.buscarBonificacionPorNombre(nombre);
    }

    public void asignarBonificacion(Propietario propietario, String nombreBonificacion, Puesto puesto) throws PeajeException {
        SBonificacion.asignarBonificacion(propietario, nombreBonificacion, puesto);
    }

    // Nuevo: permitir que la fachada agregue una bonificación al sistema
    public void agregarBonificacion(String  nombre) {
        SBonificacion.agregarBonificacion(nombre);
    }


}
