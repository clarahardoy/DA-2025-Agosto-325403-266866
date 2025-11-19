package obg_sistema_pasajes.diseno.modelo.entidad;

import observador.Observable;
import obg_sistema_pasajes.diseno.exception.PeajeException;

public abstract class Usuario extends Observable {
    private String nombreCompleto;
    private String password;
    private String cedula;

    public Usuario(String nombreCompleto, String password, String cedula) {
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getPassword() {
        return password;
    }

    public String getCedula() {
        return cedula;
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + cedula + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario otro = (Usuario) obj;
        return this.cedula != null && this.cedula.equals(otro.cedula);
    }

    public void validar() throws PeajeException {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new PeajeException("El nombre completo es requerido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new PeajeException("La contraseña es requerida");
        }
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new PeajeException("La cédula es requerida");
        }
    }
}
