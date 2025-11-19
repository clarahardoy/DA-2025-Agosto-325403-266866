package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.exception.PeajeException;

import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.estado.Estado;
import obg_sistema_pasajes.diseno.modelo.entidad.estado.EstadoHabilitado;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Propietario extends Usuario {
    public enum Eventos { CAMBIO_BONIFICACIONES, CAMBIO_ESTADO, CAMBIO_NOTIFICACIONES, CAMBIO_TRANSITOS }
    private double saldoActual;
    private double saldoMinimoAlerta;
    private Estado estado; 

    // Relaciones
    private List<Vehiculo> vehiculos = new ArrayList<>();
    private List<Asignacion> bonificacionesAsignadas = new ArrayList<>();
    private List<Transito> transitos = new ArrayList<>();
    private List<Notificacion> notificaciones = new ArrayList<>();

    public Propietario(String nombreCompleto, String password, String cedula,
        double saldoActual, double saldoMinimoAlerta) {
        super(nombreCompleto, password, cedula);
        this.saldoActual = saldoActual;
        this.saldoMinimoAlerta = saldoMinimoAlerta;
        this.estado = new EstadoHabilitado(this); // estado por defecto
    }

    public double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public double getSaldoMinimoAlerta() {
        return saldoMinimoAlerta;
    }

    public boolean tieneSaldoBajo() {
        return saldoActual < saldoMinimoAlerta;
    }

    public void setSaldoMinimoAlerta(double saldoMinimoAlerta) {
        this.saldoMinimoAlerta = saldoMinimoAlerta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    public List<Asignacion> getBonificacionesAsignadas() {
        return bonificacionesAsignadas;
    }

    public List<String> getDescripcionesBonificaciones() {
        List<String> descripciones = new ArrayList<>();
        for (Asignacion asignacion : bonificacionesAsignadas) {
            descripciones.add(asignacion.getDescripcion());
        }
        return descripciones;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }


//---------------------------- BONIFICACIONES -------------------------------------------
    public void hacerAsignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        // Verificar si ya tiene una bonificación para ese puesto
         if (tieneBonificacionEnPuesto(puesto)) {
            throw new PeajeException("Ya tiene una bonificación asignada para ese puesto");
        }

        // Crear y asignar la nueva bonificación
        Asignacion nuevaAsignacion = new Asignacion(bonificacion, puesto);
        this.bonificacionesAsignadas.add(nuevaAsignacion);
        // Avisar a los observadores que cambió la lista de bonificaciones
        avisar(Eventos.CAMBIO_BONIFICACIONES);

    }

    public double hacerAplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        if (bonificacion == null) return montoTarifa;
        return bonificacion.aplicarBonificacion(montoTarifa, vehiculo, transitosHoy, fecha);
    }

    protected void aplicarDescuentoPorBonificacionesAsignadas(Bonificacion bonificacion, double montoTarifa, Vehiculo vehiculo, List<Transito> transitosHoy, Date fecha) {
        estado.aplicarDescuentoPorBonificacionesAsignadas(bonificacion, montoTarifa, vehiculo, transitosHoy, fecha);
    }

    public void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        estado.asignarBonificacion(bonificacion, puesto);    
    }


    public boolean puedeLoguearse() throws PeajeException {
        return estado.puedeLoguearse();
    }


    public boolean tieneBonificacionEnPuesto(Puesto puesto) {
       for (Asignacion asignacion : this.getBonificacionesAsignadas()) {
            if (asignacion.getPuesto().equals(puesto)) {
                return true;
            }
        }
        return false;
    }



//-------------------------------------------TRANSITOS---------------------------------------------- 

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
    }

    public Bonificacion getBonificacionParaPuesto(Puesto puesto) {
        for (Asignacion asignacion : bonificacionesAsignadas) {
            if (asignacion.getPuesto().equals(puesto)) {
                return asignacion.getBonificacion();
            }
        }
        return null;
    }

    public Transito hacerRegistrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        if (!this.vehiculos.contains(vehiculo)) {
            throw new PeajeException("El vehículo no pertenece a este propietario");
        }
 
        Bonificacion bonificacion = getBonificacionParaPuesto(puesto);
        Transito transito = new Transito(vehiculo, puesto, this, fechaHora, bonificacion);
        
        // Delegar al estado saber si aplica o no el descuento en las bonificaciones ya asignadas
        double montoConDescuento = estado.aplicarDescuentoPorBonificacionesAsignadas(
            bonificacion, 
            transito.getMontoBase(), 
            vehiculo, 
            vehiculo.getTransitosDelDia(puesto, fechaHora),
            fechaHora
        );

        double descuento = transito.getMontoBase() - montoConDescuento;
        transito.aplicarDescuento(descuento);
        
        double montoAPagar = transito.getMontoPagado();

        if (!tieneSaldoSuficiente(montoAPagar)) {
            throw new PeajeException("Saldo insuficiente: " + this.saldoActual);
        }
        this.descontarSaldo(montoAPagar);
        
        // Agregar a las listas
        this.agregarTransito(transito);
        vehiculo.agregarTransito(transito);
        
        //se delega al estado porque si esta penalizado no se tiene que mandar (depende del estado)
        estado.registrarNotificacion("Se ha registrado un transito en el puesto " + puesto.getNombre() + " con el vehículo " + vehiculo.getMatricula());
        if (tieneSaldoBajo()) {
            estado.registrarNotificacion("Tu saldo actual es de $ " + this.saldoActual + " Te recomendamos hacer una recarga");
        }
        avisar(Eventos.CAMBIO_TRANSITOS);
        return transito;
       
    }

    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        return estado.registrarTransito(vehiculo, puesto, fechaHora);
    }

    //---------------------------- Estado------------------------------------------- 

    public boolean estaDeshabilitado() { 
        return estado.getNombre().equals("DESHABILITADO"); 
    }

    public void deshabilitar() throws PeajeException {
        estado.deshabilitar();
    }

    public boolean estaSuspendido() {
        return estado.getNombre().equals("SUSPENDIDO");
    }

    public void suspender() throws PeajeException {
        estado.suspender();
    }

    public boolean estaPenalizado() {
        return estado.getNombre().equals("PENALIZADO");
    }

    public void penalizar() throws PeajeException {
        estado.penalizar();
    }

    public boolean estaHabilitado() {
        return estado.getNombre().equals("HABILITADO");
    }

    public void habilitar() throws PeajeException {
        estado.habilitar();
    }

    public void cambiarEstado(Estado estado) throws PeajeException {
        this.estado = estado;
        hacerRegistrarNotificacion("Se ha cambiado tu estado en el sistema. Tu estado actual es " + this.estado.getNombre());
        avisar(Eventos.CAMBIO_ESTADO);
    }

    //---------------------------- Saldo------------------------------------------- 

    public boolean tieneSaldoSuficiente(double monto) {
        return this.saldoActual >= monto;
    }

    public void descontarSaldo(double monto) {
        this.saldoActual -= monto;
    }

    //---------------------------- NOTIFICACIONES------------------------------------------- 

    public void agregarNotificacion(String mensaje) {
        this.notificaciones.add(new Notificacion(mensaje));
        avisar(Eventos.CAMBIO_NOTIFICACIONES);
    }

    public void borrarNotificaciones() {
        this.notificaciones.clear();
        avisar(Eventos.CAMBIO_NOTIFICACIONES);
    }

    private String obtenerFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void hacerRegistrarNotificacion(String mensaje) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaHora = formatter.format(new java.util.Date());
        String mensajeCompleto = "[" + fechaHora + "] " + mensaje;
        this.agregarNotificacion(mensajeCompleto);
    }

    public void registrarNotificacion(String mensaje) {
        estado.registrarNotificacion(mensaje);
    }


}

