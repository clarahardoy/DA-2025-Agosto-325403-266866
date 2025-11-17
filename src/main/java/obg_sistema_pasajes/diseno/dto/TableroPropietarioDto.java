package obg_sistema_pasajes.diseno.dto;

import java.util.List;
import java.util.ArrayList;
import obg_sistema_pasajes.diseno.modelo.entidad.Propietario;
import obg_sistema_pasajes.diseno.modelo.entidad.Transito;
import obg_sistema_pasajes.diseno.modelo.entidad.Notificacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Asignacion;
import obg_sistema_pasajes.diseno.modelo.entidad.Vehiculo;

public class TableroPropietarioDto {
    public String nombreCompleto;
    public String estado;
    public double saldoActual;
    public List<AsignacionDto> bonificaciones = new ArrayList<>();
    public List<VehiculoDto> vehiculos = new ArrayList<>();
    public List<TransitoDto> transitos = new ArrayList<>();
    public List<NotificacionDto> notificaciones = new ArrayList<>();

    public TableroPropietarioDto() {}

    public static TableroPropietarioDto toDto(Propietario propietario) {
        TableroPropietarioDto dto = new TableroPropietarioDto();
        dto.nombreCompleto = propietario.getNombreCompleto();
        dto.estado = propietario.getEstado().getNombre();
        dto.saldoActual = propietario.getSaldoActual();

        for (Asignacion asignacion : propietario.getBonificacionesAsignadas()) {
            dto.bonificaciones.add(AsignacionDto.toDto(asignacion));
        }

        for (Vehiculo vehiculo : propietario.getVehiculos()) {
            dto.vehiculos.add(VehiculoDto.toDto(vehiculo));
        }

        List<Transito> transitosOrdenados = new ArrayList<>(propietario.getTransitos());
        transitosOrdenados.sort(Transito.porFechaDescendente());
        for (Transito transito : transitosOrdenados) {
            dto.transitos.add(TransitoDto.toDtoParaPropietario(transito));
        }

        for (Notificacion notificacion : Notificacion.ordenarPorFecha(propietario.getNotificaciones())) {
            dto.notificaciones.add(NotificacionDto.toDto(notificacion));
        }

        return dto;
    }
}
