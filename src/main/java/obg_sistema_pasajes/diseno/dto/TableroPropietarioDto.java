package obg_sistema_pasajes.diseno.dto;

import java.util.List;
import java.util.ArrayList;

public class TableroPropietarioDto {
    public String nombreCompleto;
    public String estado;
    public double saldoActual;
    public List<AsignacionDto> bonificaciones = new ArrayList<>();
    public List<VehiculoDto> vehiculos = new ArrayList<>();
    public List<TransitoDto> transitos = new ArrayList<>();
    public List<NotificacionDto> notificaciones = new ArrayList<>();

    public TableroPropietarioDto() {}
}
