package obg_sistema_pasajes.diseno.modelo;

import obg_sistema_pasajes.diseno.modelo.sistema.SistemaAcceso;

public class Fachada {

    private SistemaAcceso sAcceso = new SistemaAcceso();


    private static Fachada instancia; 

    //private SistemaClientes sistemaClientes;

    private Fachada() {
        //this.sistemaClientes = new SistemaClientes();
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    //DELEGACIONES
}
