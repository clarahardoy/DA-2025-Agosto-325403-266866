package obg_sistema_pasajes.diseno.controlador;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;

@RestController
@RequestMapping("/auth")
public class ControladorLogin {

    @PostMapping("/login-propietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) {
        try {
            Sesion sesion = Fachada.getInstancia().loginPropietario(cedula, password);
            logout(sesionHttp);

            sesionHttp.setAttribute("usuarioPropietario", sesion);
            return Respuesta.lista(new Respuesta("loginExitoso", "/propietario/tablero.html"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/login-admin")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) {
        try {
            // verificar si ya hay alguien logueado en esta sesion
            if(sesionHttp.getAttribute("usuarioAdmin") != null){
                throw new PeajeException("Ud ya está logueado");
            }
            
            // si hay un propietario logueado, hacerle logout
            if(sesionHttp.getAttribute("usuarioPropietario") != null){
                logout(sesionHttp);
            }
            
            Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, password);
            sesionHttp.setAttribute("usuarioAdmin", admin);
            return Respuesta.lista(new Respuesta("loginExitoso", "/admin/menu.html"));
        } catch (PeajeException e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp) throws PeajeException {
        //  admin:  sacarlo de http session 
        if (sesionHttp.getAttribute("usuarioAdmin") != null) {
            sesionHttp.removeAttribute("usuarioAdmin");
            return Respuesta.lista(new Respuesta("logout", "login-admin"));
        }
        
        //  propietario: sacar de ambos (http session y lista de sesiones)
        Sesion sesionProp = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if (sesionProp != null) {
            Fachada.getInstancia().logout(sesionProp);
            sesionHttp.removeAttribute("usuarioPropietario");
            return Respuesta.lista(new Respuesta("logout", "login-propietario"));
        }
        
        return Respuesta.lista(new Respuesta("logout", "login-admin"));
    }
}
