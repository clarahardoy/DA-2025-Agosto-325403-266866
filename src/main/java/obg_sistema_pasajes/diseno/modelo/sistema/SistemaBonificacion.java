package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;

import obg_sistema_pasajes.diseno.modelo.entidad.*;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.Bonificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.BonificacionExonerado;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.BonificacionFrecuente;
import obg_sistema_pasajes.diseno.modelo.entidad.bonificacion.BonificacionTrabajador;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public class SistemaBonificacion {
    private List<Bonificacion> bonificaciones = new ArrayList<>();
    
    public List<Bonificacion> listarBonificaciones() {
        return new ArrayList<>(bonificaciones);
    }

    public List<String> getNombres() {
        List<String> nombres = new ArrayList<>();
        for (Bonificacion b : bonificaciones) {
            nombres.add(b.getNombre());
        }
        return nombres;
    }

    public void asignarBonificacion(Propietario propietario, String nombreBonificacion, Puesto puesto) throws PeajeException {
        if (propietario == null) throw new PeajeException("No existe el propietario");
        if (puesto == null) throw new PeajeException("Debe especificar un puesto");

        Bonificacion bonificacion = buscarBonificacionPorNombre(nombreBonificacion);
        if (bonificacion == null) {
            throw new PeajeException("Debe especificar una bonificación");
        }
        propietario.asignarBonificacion(bonificacion, puesto);
    }


    public void agregarBonificacion(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return;
        String n = nombre.trim().toLowerCase();
        Bonificacion b;
        if (n.contains("trabajador")) {
            b = new BonificacionTrabajador(nombre);
        } else if (n.contains("frecuente")) {
            b = new BonificacionFrecuente(nombre);
        } else if (n.contains("exonerado")) {
            b = new BonificacionExonerado(nombre);
        } else {
            b = new BonificacionFrecuente(nombre);
        }
        bonificaciones.add(b);
    }


    public Bonificacion buscarBonificacionPorNombre(String nombre) {
        for (Bonificacion b : bonificaciones) {
            if (b.getNombre().equals(nombre)) {
                return b;
            }
        }
        return null;
    }
}