package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.exception.PeajeException;

import obg_sistema_pasajes.diseno.dto.TableroPropietarioDto;
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

    public List<Transito> getTransitos() {
        return transitos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }



    //-------------------Tablero DTO-----------------------------------------------------------------
    public TableroPropietarioDto obtenerTableroDto() {

        TableroPropietarioDto dto = new TableroPropietarioDto();
        dto.nombreCompleto = this.getNombreCompleto();
        dto.estado = this.estado.getNombre().toString();
        dto.saldoActual = this.saldoActual;

        // mapear 
        for (Asignacion asignacion : this.bonificacionesAsignadas) {
            dto.bonificaciones.add(asignacion.toDto());
        }

        for (Vehiculo vehiculo : this.vehiculos) {
            dto.vehiculos.add(vehiculo.toDto());
        }
    
        for (Transito transito : obtenerTransitosOrdenados()) {
            dto.transitos.add(transito.toDto());
        }

        // ordenar notificaciones por fechaHora descendente 
        this.notificaciones.sort((notificacion1, notificacion2) -> {
            if (notificacion1.getFechaHora() == null && notificacion2.getFechaHora() == null) return 0;
            if (notificacion1.getFechaHora() == null) return 1;
            if (notificacion2.getFechaHora() == null) return -1;
            return notificacion2.getFechaHora().compareTo(notificacion1.getFechaHora());
        });
        
        for (Notificacion notif : this.notificaciones) {
            dto.notificaciones.add(notif.toDto());
        }

    return dto;
    }


//---------------------------- BONIFICACIONES -------------------------------------------
    public void asignarBonificacion(Bonificacion bonificacion, Puesto puesto) throws PeajeException {
        // Validar que el propietario esté habilitado
        if (estaDeshabilitado()) {
            throw new PeajeException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
        }

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

    public void validarPuedeRealizarTransito() throws PeajeException {
        if (estaDeshabilitado()) {
            throw new PeajeException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
        }
        if (estaSuspendido()) {
            throw new PeajeException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
        }
    }

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

    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        if (!this.vehiculos.contains(vehiculo)) {
            throw new PeajeException("El vehículo no pertenece a este propietario");
        }
 
        validarPuedeRealizarTransito();

        Bonificacion bonificacion = getBonificacionParaPuesto(puesto);
        Transito transito = new Transito(vehiculo, puesto, this, fechaHora, bonificacion);
        
        double montoPagado = transito.getMontoPagado();
        if (!tieneSaldoSuficiente(montoPagado)) {
            throw new PeajeException("Saldo insuficiente: " + this.saldoActual);
        }
        this.descontarSaldo(montoPagado);
        
        // Agregar a las listas
        this.transitos.add(transito);
        vehiculo.agregarTransito(transito);
        
        // Notificaciones
        if (!this.estado.getNombre().equals("PENALIZADO")) {
            registrarNotificacionTransito(puesto, vehiculo);
            
            if (saldoActual < saldoMinimoAlerta) {
                registrarNotificacionSaldoBajo();
            }
        }
        
        avisar(Eventos.CAMBIO_TRANSITOS);
        return transito;
    }

    private List<Transito> obtenerTransitosOrdenados() {
        List<Transito> copia = new ArrayList<>(this.transitos);
        copia.sort(Transito.porFechaDescendente());
        return copia;
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
        hacerRegistrarNotificacion();
        avisar(Eventos.CAMBIO_ESTADO);
    }


    protected void hacerRegistrarTransito() {
        this.estado.registrarTransito();
    }

    protected void hacerAsignarBonificacion() {
        this.estado.asignarBonificacion();
    }

    protected void hacerAplicarDescuento() {
        this.estado.aplicarDescuento();
    }

    protected void hacerRegistrarNotificacion() {
        this.estado.registrarNotificacion();
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

    public void verificarYNotificarSaldoBajo() {
        if (this.saldoActual < this.saldoMinimoAlerta) {
            String fecha = obtenerFechaHoraActual();
            String mensaje = fecha + " Tu saldo actual es de $ " + this.saldoActual + 
                " Te recomendamos hacer una recarga";
            agregarNotificacion(mensaje);
        }
    }

    private void registrarNotificacionTransito(Puesto puesto, Vehiculo vehiculo) {
        String fecha = obtenerFechaHoraActual();
        String mensaje = fecha + " Pasaste por el puesto " + puesto.getNombre() + 
                        " con el vehículo " + vehiculo.getMatricula();
        agregarNotificacion(mensaje);
    }

    private void registrarNotificacionSaldoBajo() {
        String fecha = obtenerFechaHoraActual();
        String mensaje = fecha + " Tu saldo actual es de $ " + saldoActual + 
                        " Te recomendamos hacer una recarga";
        agregarNotificacion(mensaje);
    }

    public void borrarNotificaciones() {
        this.notificaciones.clear();
        avisar(Eventos.CAMBIO_NOTIFICACIONES);
    }

    private String obtenerFechaHoraActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}

