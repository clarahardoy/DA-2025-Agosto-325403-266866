package obg_sistema_pasajes.diseno.controlador;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import obg_sistema_pasajes.diseno.exception.PeajeException;

@RestController
@RequestMapping("/auth")
public class ControladorLogin {

    @PostMapping("/login")
    public List<Respuesta> login(HttpSession session, @RequestParam String usuario, @RequestParam String password) throws PeajeException {
       //Usuario usuario = Fachada.getInstancia().login(usuario, password);
       // if (usuario == null) throw new PeajeException("Credenciales incorrectas");
       // session.setAttribute("usuario", usuario);
       return Respuesta.lista(new Respuesta("Login exitoso", "menu.html"));
    }
}
