package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Sesion;

@RestController
@RequestMapping("/tablero")
public class ControladorTablero {
    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "usuarioPropietario") Sesion sesion){
        Propietario propietario = (Propietario) sesion.getUsuario();

        return Respuesta.lista(
            new Respuesta("nombreCompleto", propietario.getNombreCompleto()),
            new Respuesta("estadoPropietario", propietario.getEstado().getNombre()),
            new Respuesta("saldoActual", propietario.getSaldoActual())
            //new Respuesta("listaBonificaciones", propietario.getBonificaciones()),
           // new Respuesta("listaVehiculos", propietario.getVehiculos()),
           // new Respuesta("listaTransitos", propietario.getTransitos()),
           // new Respuesta("listaNotificaciones", propietario.getNotificaciones())
        );
        
    }
}
