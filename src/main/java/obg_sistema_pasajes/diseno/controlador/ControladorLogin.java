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
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException {
    Sesion sesion = Fachada.getInstancia().loginPropietario(cedula, password);

       // si hay una sesion activa la cierro
       logout(sesionHttp);

       // guardo la sesion de la logica en la sesionHttp
       sesionHttp.setAttribute("usuarioPropietario", sesion);
       return Respuesta.lista(new Respuesta("loginExitoso", "menu.html"));
    }

    @PostMapping("/login-admin")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String cedula, @RequestParam String password) throws PeajeException {
        Administrador admin = Fachada.getInstancia().loginAdministrador(cedula, password);
        // guardo el admin en la sesionHttp
        sesionHttp.setAttribute("usuarioAdmin", admin);
        return Respuesta.lista(new Respuesta("loginExitoso", "monitor-actividad.html"));
    }

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp) throws PeajeException {
        Sesion sesion = (Sesion) sesionHttp.getAttribute("usuarioPropietario");
        if (sesion != null) {
            Fachada.getInstancia().logout(sesion);
            sesionHttp.removeAttribute("usuarioPropietario");
        }
        return Respuesta.lista(new Respuesta("paginaLogin", "login.html"));
    }
}
