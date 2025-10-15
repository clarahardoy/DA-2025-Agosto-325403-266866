/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package obg_sistema_pasajes.diseno.modelo.entidad;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Sesion {
    
    private Date fechaIngreso = new Date();
    private Usuario usuario;

    public Sesion(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    
    
}
