package obg_sistema_pasajes.diseno.modelo.sistema;

import java.util.ArrayList;
import java.util.List;

import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.*;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public class SistemaBonificacion {
    private List<Bonificacion> bonificaciones = new ArrayList<>();
    
    public void inicializarBonificaciones() {
        // Crear las tres bonificaciones definidas en la letra
        bonificaciones.add(new BonificacionTrabajador("Bonificación trabajador"));
        bonificaciones.add(new BonificacionFrecuente("Bonificación frecuente"));
        bonificaciones.add(new BonificacionExonerado("Bonificación exonerado"));
    }

    public List<Bonificacion> listarBonificaciones() {
        return new ArrayList<>(bonificaciones);
    }

    public void asignarBonificacion(String cedula, String nombreBonificacion, String nombrePuesto) throws PeajeException {

        Propietario propietario = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietario == null) throw new PeajeException("No existe el propietario");

        Bonificacion bonificacion = buscarBonificacionPorNombre(nombreBonificacion);
        if (bonificacion == null) {
            throw new PeajeException("Debe especificar una bonificación");
        }
        
        Puesto puesto = Fachada.getInstancia().obtenerPuestoPorNombre(nombrePuesto);
        if (puesto == null) throw new PeajeException("Debe especificar un puesto");

        propietario.asignarBonificacion(bonificacion, puesto);
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