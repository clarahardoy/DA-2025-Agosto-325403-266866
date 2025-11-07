package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import obg_sistema_pasajes.diseno.exception.PeajeException;

import obg_sistema_pasajes.diseno.dto.AsignacionDto;
import obg_sistema_pasajes.diseno.dto.NotificacionDto;
import obg_sistema_pasajes.diseno.dto.TableroPropietarioDto;
import obg_sistema_pasajes.diseno.dto.TransitoDto;
import obg_sistema_pasajes.diseno.dto.VehiculoDto;

public class Propietario extends Usuario {
    public enum Eventos { cambioBonificaciones }
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
        this.estado = new Estado("Habilitado"); // estado por defecto
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
    dto.estado = this.estado.getNombre();
    dto.saldoActual = this.saldoActual;

    // mapear 
    for (Asignacion a : this.bonificacionesAsignadas) {
        dto.bonificaciones.add(new AsignacionDto(
            a.getFechaAsignada().getTime(),
            a.getBonificacion().getNombre(),
            a.getPuesto().getNombre()
        ));
    }
    for (Vehiculo v : this.vehiculos) {
        String categoriaNombre = v.getCategoria() != null ? v.getCategoria().getNombre() : null;
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
        // Avisar a los observadores que cambió la lista de bonificaciones
        avisar(Eventos.cambioBonificaciones);
    }


     public boolean estaDeshabilitado() { 
        return estado.getNombre().equals("Deshabilitado"); 
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
}
