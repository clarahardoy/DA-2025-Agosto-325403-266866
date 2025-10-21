package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;

import obg_sistema_pasajes.diseno.dto.AsignacionDto;
import obg_sistema_pasajes.diseno.dto.NotificacionDto;
import obg_sistema_pasajes.diseno.dto.TableroPropietarioDto;
import obg_sistema_pasajes.diseno.dto.TransitoDto;
import obg_sistema_pasajes.diseno.dto.VehiculoDto;

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
        dto.bonificaciones.add(new AsignacionDto(a.getFechaAsignada().getTime()));
    }
    for (Vehiculo v : this.vehiculos) {
        String categoriaNombre = v.getCategoria() != null ? v.getCategoria().getNombre() : null;
        dto.vehiculos.add(new VehiculoDto(v.getMatricula(), v.getModelo(), v.getColor(), categoriaNombre));
    }
    for (Transito t : this.transitos) {
        dto.transitos.add(new TransitoDto(t.getMontoBonificado()));
    } 
    for (Notificacion n : this.notificaciones) {
        dto.notificaciones.add(new NotificacionDto(n.getMensaje()));
    }

    return dto;
    }

    public void borrarNotificaciones() {
    this.notificaciones.clear();
    }

    // mantener validar del padre (si se desea extender, sobreescribir)
}
