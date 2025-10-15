package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;

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

    // mantener validar del padre (si se desea extender, sobreescribir)
}
