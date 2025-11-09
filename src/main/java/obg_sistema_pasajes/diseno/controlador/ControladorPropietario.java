package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Scope;

import jakarta.servlet.http.HttpSession;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import observador.Observable;
import observador.Observador;
import obg_sistema_pasajes.diseno.ConexionNavegador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/propietario")
@Scope("session")
public class ControladorPropietario implements Observador{

    private Propietario propietario;
    private final ConexionNavegador conexionNavegador;

    public ControladorPropietario(@Autowired ConexionNavegador conexionNavegador){
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    @PostMapping("/tablero")
    public List<Respuesta> obtenerTablero(HttpSession sesionHttp) throws PeajeException{
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if(sesion == null) throw new PeajeException("Usuario no autenticado");


        Propietario p = (Propietario) sesion.getUsuario();
        Object dto = p.obtenerTableroDto();
        return Respuesta.lista(new Respuesta("tableroData", dto));
    }

    @PostMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(HttpSession sesionHttp) throws PeajeException{
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if(sesion == null) throw new PeajeException("Usuario no autenticado");

        if(propietario!=null) propietario.quitarObservador(this);
        propietario = (Propietario) sesion.getUsuario();
        propietario.agregarObservador(this);

        return Respuesta.lista(new Respuesta("tableroData", propietario.obtenerTableroDto()));
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(propietario!=null) propietario.quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento!=null && (evento.equals(Propietario.Eventos.CAMBIO_BONIFICACIONES) || 
                            evento.equals(Propietario.Eventos.CAMBIO_NOTIFICACIONES) ||
                            evento.equals(Propietario.Eventos.CAMBIO_ESTADO))){
            Propietario p = (Propietario) origen;
            conexionNavegador.enviarJSON(Respuesta.lista(new Respuesta("tableroData", p.obtenerTableroDto())));
        }
    }

    @PostMapping("/borrar-notificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession sesionHttp) throws PeajeException{
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if(sesion == null) throw new PeajeException("Usuario no autenticado");
        
        Propietario p = (Propietario) sesion.getUsuario();
        if(p.getNotificaciones() == null || p.getNotificaciones().isEmpty()){
            throw new PeajeException("No hay notificaciones para borrar");
        }
        p.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("mensaje", "Notificaciones borradas correctamente"));
    }
}
