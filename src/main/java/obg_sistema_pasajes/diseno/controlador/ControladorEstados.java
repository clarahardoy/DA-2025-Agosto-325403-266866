package obg_sistema_pasajes.diseno.controlador;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;
import observador.Observador;
import obg_sistema_pasajes.diseno.ConexionNavegador;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.estado.TipoEstado;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.util.List;
import observador.Observable;
import org.springframework.web.bind.annotation.RequestParam;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.dto.NombreDto;

@RestController
@RequestMapping("/estados")
@Scope("session")

public class ControladorEstados implements Observador {

    private List<String> estados;
    private final ConexionNavegador conexionNavegador; 
    private Propietario propietario;

    public ControladorEstados(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin") Administrador admin) throws PeajeException {
        return Respuesta.lista(estados());
    }

    @PostMapping("/buscar-propietario")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula) throws PeajeException {
        Propietario propietarioEncontrado = Fachada.getInstancia().obtenerPropietarioPorCedula(cedula);
        if (propietarioEncontrado == null) {
            throw new PeajeException("no existe el propietario");
        }
        
        if (propietario != null) {
            propietario.quitarObservador(this);
        }
        
        propietario = propietarioEncontrado;
        propietario.agregarObservador(this);
        
        return Respuesta.lista(
            new Respuesta("propietarioNombre", propietario.getNombreCompleto()),
            estados(),
            new Respuesta("estadoActual", propietario.getEstado().getNombre())
        );
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(propietario!=null) propietario.quitarObservador(this);
    }

    @PostMapping("/cambiar-estado")
    public List<Respuesta> setEstado(@RequestParam int posEstado) throws PeajeException {
        if (propietario == null) {
            throw new PeajeException("Debe buscar un propietario primero");
        }
        
        switch(posEstado){
            case 0 : propietario.deshabilitar(); break;  // DESHABILITADO
            case 1 : propietario.suspender(); break;     // SUSPENDIDO
            case 2 : propietario.penalizar(); break;    // PENALIZADO
            case 3 : propietario.habilitar(); break;    // HABILITADO
            default: throw new PeajeException("Estado inválido");
        }
        
        return Respuesta.lista(estadoActual(),new Respuesta("mensaje","Se cambió el estado del propietario"));
    }

    private Respuesta estadoActual() {
        return new Respuesta("estadoActual", propietario.getEstado().getNombre());
      }
   

    private Respuesta estados(){
        List<TipoEstado> tiposEstado = Fachada.getInstancia().getTiposEstado();
        estados = new ArrayList<String>();
        List<NombreDto> dtos = new ArrayList<NombreDto>();
        for (TipoEstado tipoEstado : tiposEstado){
            String nombre = tipoEstado.getNombre();
            estados.add(nombre);
            dtos.add(new NombreDto(nombre));
        }  
        return new Respuesta("estados", dtos);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (evento != null && evento.equals(Propietario.Eventos.CAMBIO_ESTADO)) {
            if (propietario != null) {
                conexionNavegador.enviarJSON(Respuesta.lista(estadoActual()));
            }
        }
    }


}