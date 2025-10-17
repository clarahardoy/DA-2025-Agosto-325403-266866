package obg_sistema_pasajes.diseno.controlador;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;

@RestController
@RequestMapping("/administrador")

public class ControladorAdministrador {

    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioAdmin") Administrador admin){

         return Respuesta.lista(
            new Respuesta("nombreAdministrador",admin.getNombreCompleto())
            );
        
    }
}

