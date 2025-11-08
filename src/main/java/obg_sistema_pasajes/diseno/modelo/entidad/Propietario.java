package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.exception.PeajeException;

import obg_sistema_pasajes.diseno.dto.AsignacionDto;
import obg_sistema_pasajes.diseno.dto.NotificacionDto;
import obg_sistema_pasajes.diseno.dto.TableroPropietarioDto;
import obg_sistema_pasajes.diseno.dto.TransitoDto;
import obg_sistema_pasajes.diseno.dto.VehiculoDto;
import obg_sistema_pasajes.diseno.modelo.entidad.Estado.NombreEstado;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Propietario extends Usuario {
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
        this.estado = new Estado(NombreEstado.DESHABILITADO); // estado por defecto
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

    public List<Asignacion> getBonificacionesAsignadas() {
        return bonificacionesAsignadas;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public TableroPropietarioDto obtenerTableroDto() {
    TableroPropietarioDto dto = new TableroPropietarioDto();
    dto.nombreCompleto = this.getNombreCompleto();
    dto.estado = this.estado.getNombre().toString();
    dto.saldoActual = this.saldoActual;

    // mapear 
    for (Asignacion a : this.bonificacionesAsignadas) {
        dto.bonificaciones.add(new AsignacionDto(a.getFechaAsignada().getTime()));
    }
    for (Vehiculo v : this.vehiculos) {
        String categoriaNombre = v.getCategoria() != null ? v.getCategoria().getNombreCategoria().toString() : null;
        dto.vehiculos.add(new VehiculoDto(v.getMatricula(), v.getModelo(), v.getColor(), categoriaNombre));
    }
    for (Transito t : this.transitos) {
        dto.transitos.add(new TransitoDto());
    } 
    for (Notificacion n : this.notificaciones) {
        dto.notificaciones.add(new NotificacionDto(n.getMensaje()));
    }

    return dto;
    }

    public void borrarNotificaciones() {
    this.notificaciones.clear();
    }

     public void descontarSaldo(double monto) {
        this.saldoActual -= monto;
    }

    //-------------------------------

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
    }


    public boolean estaDeshabilitado() { 
        return estado.getNombre().equals(NombreEstado.DESHABILITADO); 
    }

    public boolean estaSuspendido() {
        return estado.getNombre().equals(NombreEstado.SUSPENDIDO);
    }

    public boolean estaPenalizado() {
        return estado.getNombre().equals(NombreEstado.PENALIZADO);
    }

    public boolean estaHabilitado() {
        return estado.getNombre().equals(NombreEstado.HABILITADO);
    }

    public boolean tieneSaldoSuficiente(double monto) {
        return this.saldoActual >= monto;
    }

    public void validarPuedeRealizarTransito() throws PeajeException {
        if (estaDeshabilitado()) {
            throw new PeajeException("El propietario del vehículo está deshabilitado, no puede realizar tránsitos");
        }
        if (estaSuspendido()) {
            throw new PeajeException("El propietario del vehículo está suspendido, no puede realizar tránsitos");
        }
    }

    public void agregarNotificacion(String mensaje) {
        this.notificaciones.add(new Notificacion(mensaje));
    }

    public void verificarYNotificarSaldoBajo() {
        if (this.saldoActual < this.saldoMinimoAlerta) {
            String mensaje = new Date().toString() + 
                " Tu saldo actual es de $ " + this.saldoActual + 
                " Te recomendamos hacer una recarga";
            agregarNotificacion(mensaje);
        }
    }

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
    }

    public List<Transito> getTransitosDelDia(Date fecha) {
        List<Transito> transitosDelDia = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaBuscada = sdf.format(fecha);
        
        for (Transito t : transitos) {
            if (t.getFechaHora() != null) {
                String fechaTransito = sdf.format(t.getFechaHora());
                if (fechaBuscada.equals(fechaTransito)) {
                    transitosDelDia.add(t);
                }
            }
        }
        return transitosDelDia;
    }

    public boolean tieneBonificacionEnPuesto(Puesto puesto) {
       for (Asignacion asignacion : this.getBonificacionesAsignadas()) {
            if (asignacion.getPuesto().equals(puesto)) {
                return true;
            }
        }
        return false;
    }

    public Bonificacion getBonificacionParaPuesto(Puesto puesto) {
        for (Asignacion asignacion : bonificacionesAsignadas) {
            if (asignacion.getPuesto().equals(puesto)) {
                return asignacion.getBonificacion();
            }
        }
        return null;
    }

    // mantener validar del padre (si se desea extender, sobreescribir)

    public Transito registrarTransito(Vehiculo vehiculo, Puesto puesto, Date fechaHora) throws PeajeException {
        if (!this.vehiculos.contains(vehiculo)) {
            throw new PeajeException("El vehículo no pertenece a este propietario");
        }
 
        validarPuedeRealizarTransito();

        Tarifa tarifa = puesto.obtenerTarifaPorCategoria(vehiculo.getCategoria());
        double montoBaseTarifa = tarifa.getMonto();
        
        double montoFinal = montoBaseTarifa;
        Bonificacion bonificacion = getBonificacionParaPuesto(puesto);
        boolean bonificacionFueAplicada = false;

        if (estado.getNombre() != NombreEstado.PENALIZADO && bonificacion != null) {
            montoFinal = bonificacion.calcularMontoConDescuento(
                montoBaseTarifa, vehiculo, this.transitos);
            bonificacionFueAplicada = (montoFinal != montoBaseTarifa);
        }

        if (!tieneSaldoSuficiente(montoFinal)) {
            throw new PeajeException("Saldo insuficiente: " + this.saldoActual);
        }
        this.descontarSaldo(montoFinal);
        double montoBonificado = montoBaseTarifa - montoFinal;
        
        Transito transito = new Transito(
            vehiculo, 
            puesto, 
            this,           
            tarifa,       
            montoFinal,   
            bonificacion, 
            bonificacionFueAplicada, 
            fechaHora,
            montoBonificado
        );
        this.transitos.add(transito);
        
        //notificaciones
        if (this.estado.getNombre() != NombreEstado.PENALIZADO) {
            registrarNotificacionTransito(puesto, vehiculo);
            
            if (saldoActual < saldoMinimoAlerta) {
                registrarNotificacionSaldoBajo();
            }
        }
        
        return transito;
    }
    
    private void registrarNotificacionTransito(Puesto puesto, Vehiculo vehiculo) {
        String mensaje = "Pasaste por el puesto " + puesto.getNombre() + 
                        " con el vehículo " + vehiculo.getMatricula();
        notificaciones.add(new Notificacion(mensaje));
    }

    private void registrarNotificacionSaldoBajo() {
        String mensaje = "Tu saldo actual es de $ " + saldoActual + 
                        " Te recomendamos hacer una recarga";
        notificaciones.add(new Notificacion(mensaje));
    }
}

