package obg_sistema_pasajes.diseno.modelo;

public class Fachada {
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
