package obg_sistema_pasajes.diseno.modelo.entidad;

public class UsuarioPropietario {
    private String nombreCompleto;
    private String password; 
    private String cedula; 


    public UsuarioPropietario(String nombreCompleto, String password, String cedula) {
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

    public boolean equals(Object obj) {
        UsuarioPropietario otro = (UsuarioPropietario) obj;
        return this.cedula.equals(otro.cedula);
    }

    public void validar() throws IllegalArgumentException {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es requerido");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula es requerida");
        }
    }
}
