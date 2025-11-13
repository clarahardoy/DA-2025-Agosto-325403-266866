package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.exception.PeajeException;

import obg_sistema_pasajes.diseno.dto.NotificacionDto;
import obg_sistema_pasajes.diseno.dto.TableroPropietarioDto;
import obg_sistema_pasajes.diseno.dto.TransitoDto;
import obg_sistema_pasajes.diseno.dto.VehiculoDto;
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
    for (Asignacion a : this.bonificacionesAsignadas) {
        dto.bonificaciones.add(a.toDto());
    }
    for (Vehiculo v : this.vehiculos) {
        String categoriaNombre = v.getCategoria() != null ? v.getCategoria().getNombreCategoria().toString() : null;
        int contador = v.getContadorTransitos();
        double gastado = v.getTotalGastado();
        dto.vehiculos.add(new VehiculoDto(v.getMatricula(), v.getModelo(), v.getColor(), categoriaNombre, contador, gastado));
    }
    // ordenar transitos por fechaHora descendente 
    this.transitos.sort((a, b) -> {
        if (a.getFechaHora() == null && b.getFechaHora() == null) return 0;
        if (a.getFechaHora() == null) return 1;
        if (b.getFechaHora() == null) return -1;
        return b.getFechaHora().compareTo(a.getFechaHora());
    });

    SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
    for (Transito t : this.transitos) {
        String puestoNombre = t.getPuesto() != null ? t.getPuesto().getNombre() : "-";
        String matricula = t.getVehiculo() != null ? t.getVehiculo().getMatricula() : "-";
        String categoriaNombre = t.getCategoria() != null ? t.getCategoria().getNombreCategoria().toString() : "-";
        String nombreBonificacion = t.getBonificacion() != null ? t.getBonificacion().getNombre() : "Ninguna";
        double montoTarifa = t.getTarifa() != null ? t.getTarifa().getMonto() : 0.0;
        double montoBonificado = t.getMontoBonificado();
        double montoPagado = t.getMontoPagado();
        String fecha = t.getFechaHora() != null ? sdfFecha.format(t.getFechaHora()) : "-";
        String hora = t.getFechaHora() != null ? sdfHora.format(t.getFechaHora()) : "-";

        dto.transitos.add(new TransitoDto(puestoNombre, matricula, categoriaNombre,
                nombreBonificacion, montoTarifa, montoBonificado, montoPagado, fecha, hora));
    }
    // ordenar notificaciones por fechaHora descendente 
    this.notificaciones.sort((a, b) -> {
        if (a.getFechaHora() == null && b.getFechaHora() == null) return 0;
        if (a.getFechaHora() == null) return 1;
        if (b.getFechaHora() == null) return -1;
        return b.getFechaHora().compareTo(a.getFechaHora());
    });
    for (Notificacion n : this.notificaciones) {
        dto.notificaciones.add(new NotificacionDto(n.getMensaje()));
    }

    return dto;
    }
//-------------------------------------------------------------------------------------------------------









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

        // Resolver bonificación asignada (el experto que elige es el Propietario)
        Bonificacion bonificacion = getBonificacionParaPuesto(puesto);
        // Crear el tránsito (el experto que aplica es Transito)
        Transito transito = new Transito(vehiculo, puesto, this, fechaHora, bonificacion);
        
        // Verificar saldo y descontar
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = sdf.format(new Date());
            String mensaje = fecha + " Tu saldo actual es de $ " + this.saldoActual + 
                " Te recomendamos hacer una recarga";
            agregarNotificacion(mensaje);
        }
    }

    private void registrarNotificacionTransito(Puesto puesto, Vehiculo vehiculo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = sdf.format(new Date());
        String mensaje = fecha + " Pasaste por el puesto " + puesto.getNombre() + 
                        " con el vehículo " + vehiculo.getMatricula();
        agregarNotificacion(mensaje);
    }

    private void registrarNotificacionSaldoBajo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fecha = sdf.format(new Date());
        String mensaje = fecha + " Tu saldo actual es de $ " + saldoActual + 
                        " Te recomendamos hacer una recarga";
        agregarNotificacion(mensaje);
    }

    public void borrarNotificaciones() {
        this.notificaciones.clear();
        avisar(Eventos.CAMBIO_NOTIFICACIONES);
    }
}

