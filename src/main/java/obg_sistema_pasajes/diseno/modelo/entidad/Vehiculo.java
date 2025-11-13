package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Vehiculo {
    private String matricula;
    private String modelo;
    private String color;
    private CategoriaVehiculo categoria;
    private Propietario propietario;
    private List<Transito> transitos = new ArrayList<>();

    public Vehiculo(String matricula, String modelo, String color, CategoriaVehiculo categoria) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.color = color;
        this.categoria = categoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public String getColor() {
        return color;
    }

    //---------
    public CategoriaVehiculo getCategoria() {
        return categoria;
    }

    public Propietario getPropietario() {
        return propietario; 
    }
    //---------

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategoria(CategoriaVehiculo categoria) {
        this.categoria = categoria;
    }

    public List<Transito> getTransitos() {
        return transitos;
    }

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
    }

    // Obtiene el contador de tránsitos realizados por este vehículo
    public int getContadorTransitos() {
        return transitos.size();
    }

    //Obtiene el total gastado en tránsitos por este vehículo
    public double getTotalGastado() {
        double total = 0.0;
        for (Transito t : transitos) {
            if (t != null) {
                total += t.getMontoPagado();
            }
        }
        return total;
    }

    // Obtiene los tránsitos del día para este vehículo en un puesto específico
    public List<Transito> getTransitosDelDia(Puesto puesto, Date fecha) {
        List<Transito> transitosDelDia = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fechaBuscada = sdf.format(fecha);

        for (Transito t : transitos) {
            if (t != null && t.getFechaHora() != null) {
                String fechaTransito = sdf.format(t.getFechaHora());
                if (fechaBuscada.equals(fechaTransito)) {
                    boolean matchPuesto = (puesto == null || (t.getPuesto() != null && t.getPuesto().equals(puesto)));
                    if (matchPuesto) {
                        transitosDelDia.add(t);
                    }
                }
            }
        }
        return transitosDelDia;
    }
}