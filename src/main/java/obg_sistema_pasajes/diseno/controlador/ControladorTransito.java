package obg_sistema_pasajes.diseno.controlador;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import obg_sistema_pasajes.diseno.modelo.Fachada;
import obg_sistema_pasajes.diseno.exception.PeajeException;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Puesto;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.dto.TransitoDto;
import obg_sistema_pasajes.diseno.ConexionNavegador;
import obg_sistema_pasajes.diseno.modelo.entidad.Administrador;
import observador.Observador;
import observador.Observable;

@RestController
@RequestMapping("/transito")
@Scope("session")
public class ControladorTransito implements Observador {

    private Administrador administradorSesion;
    private final ConexionNavegador conexionNavegador;

    public ControladorTransito(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }


    @PostMapping("/vista-conectada")
    public List<Respuesta> inicializarVista(HttpSession sesion) throws PeajeException{
        Administrador admin = (Administrador) sesion.getAttribute("usuarioAdmin");
        if (admin == null) {
            return Respuesta.lista(new Respuesta("paginaLogin", "/login/administrador.html"));
        }
        if (administradorSesion != null) administradorSesion.quitarObservador(this);
        administradorSesion = admin;
        administradorSesion.agregarObservador(this);
        return buildInicialData();
    }

    private List<Respuesta> buildInicialData() {
        return Respuesta.lista(
            new Respuesta("vistaConectada", "Vista conectada correctamente"),
            new Respuesta("puestos", Fachada.getInstancia().listarPuestos())
        );
    }

    @PostMapping("/emular")
    public List<Respuesta> emularTransito(
            @RequestParam String puestoNombre,
            @RequestParam String matricula,
            @RequestParam String fechaHora
            ) {
        
        try {
            Fachada fachada = Fachada.getInstancia();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date fechaHoraTransito = dateFormat.parse(fechaHora);
            
            Vehiculo vehiculo = fachada.obtenerVehiculoPorMatricula(matricula);
            if (vehiculo == null) {
                throw new PeajeException("No existe el vehículo");
            }

            Propietario propietario = vehiculo.getPropietario();
            if (propietario == null) {
                throw new PeajeException("El vehículo no tiene propietario asignado");
            }

            Puesto puesto = fachada.obtenerPuestoPorNombre(puestoNombre);
            Transito transito = propietario.registrarTransito(vehiculo, puesto, fechaHoraTransito);

            return Respuesta.lista(
                new Respuesta("resultadoTransito", TransitoDto.toDtoParaAdmin(transito))
            );
            
        } catch (PeajeException e) {
            return Respuesta.lista(
                new Respuesta("error", e.getMessage())
            );
        } catch (Exception e) {
            return Respuesta.lista(
                new Respuesta("error", "Error inesperado: " + e.getMessage())
            );
        }
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(administradorSesion!=null) administradorSesion.quitarObservador(this);
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        conexionNavegador.enviarJSON(buildInicialData());
    }
}
