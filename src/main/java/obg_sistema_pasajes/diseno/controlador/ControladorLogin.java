package obg_sistema_pasajes.diseno.controlador;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpSession;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import obg_sistema_pasajes.diseno.ConexionNavegador;

@RestController
@RequestMapping("/auth")
public class ControladorLogin {

    @PostMapping("/login-propietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password, @Autowired ConexionNavegador conexionNavegador) {
        try {
            Sesion sesion = Fachada.getInstancia().loginPropietario(cedula, password);
            // si hay una sesion activa la cierro (esto cierra el SSE también)
            logout(sesionHttp, conexionNavegador);

            sesionHttp.setAttribute("usuarioPropietario", sesion);
            return Respuesta.lista(new Respuesta("loginExitoso", "/propietario/tablero.html"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/login-admin")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password, @Autowired ConexionNavegador conexionNavegador) {
        try {
            if(sesionHttp.getAttribute("usuarioAdmin") != null){
                return Respuesta.lista(new Respuesta("yaLogueado", "/admin/menu.html"));
            }
            
            // Si hay un propietario logueado, cerrar sesión y SSE
            if(sesionHttp.getAttribute("usuarioPropietario") != null){
                logout(sesionHttp, conexionNavegador);
            }
            
            Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, password);
            sesionHttp.setAttribute("usuarioAdmin", admin);
            return Respuesta.lista(new Respuesta("loginExitoso", "/admin/menu.html"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp, @Autowired ConexionNavegador conexionNavegador) throws PeajeException {
        // Cerrar la conexión SSE antes de limpiar la sesión
        try {
            conexionNavegador.cerrarConexion();
        } catch (Exception e) {
            // Si no hay conexión SSE, no pasa nada
        }
        
        Object admin = sesionHttp.getAttribute("usuarioAdmin");
        if (admin != null) {
            sesionHttp.removeAttribute("usuarioAdmin");
            return Respuesta.lista(new Respuesta("logout", "login-admin"));
        }
        
        Sesion sesionProp = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if (sesionProp != null) {
            Fachada.getInstancia().logout(sesionProp);
            sesionHttp.removeAttribute("usuarioPropietario");
            return Respuesta.lista(new Respuesta("logout", "login-propietario"));
        }
        
        return Respuesta.lista(new Respuesta("logout", "login-admin"));
    }
}
