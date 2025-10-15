package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/menu")
public class ControladorMenu {

    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuario") Usuario usuario){

         return Respuesta.lista(new Respuesta("nombreCompleto", usuario.getNombreCompleto()));
        
    }
}

