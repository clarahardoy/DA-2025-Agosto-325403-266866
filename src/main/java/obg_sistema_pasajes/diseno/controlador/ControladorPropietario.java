package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;

@RestController
@RequestMapping("/propietario")
public class ControladorPropietario {

    @PostMapping("/tablero")
    public List<Respuesta> obtenerTablero(HttpSession sesionHttp) throws PeajeException{
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if(sesion == null) throw new PeajeException("Usuario no autenticado");


        Propietario p = (Propietario) sesion.getUsuario();
        Object dto = p.obtenerTableroDto();
        return Respuesta.lista(new Respuesta("tableroData", dto));
    }

    @PostMapping("/borrar-notificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession sesionHttp) throws PeajeException{
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if(sesion == null) throw new PeajeException("Usuario no autenticado");

        
        Propietario p = (Propietario) sesion.getUsuario();
        if(p.getNotificaciones() == null || p.getNotificaciones().isEmpty()){
            return Respuesta.lista(new Respuesta("borrarResultado", "No hay notificaciones para borrar"));
        }
        p.borrarNotificaciones();
        return Respuesta.lista(new Respuesta("borrarResultado", "OK"));
    }
}
