package obg_sistema_pasajes.diseno.modelo.entidad;

public class Transito {
    private int montoBonificado;

    public Transito(int montoBonificado) {
        this.montoBonificado = montoBonificado;
    }

    public int getMontoBonificado() {
        return montoBonificado;
    }
    
    public void setMontoBonificado(int montoBonificado) {
        this.montoBonificado = montoBonificado;
    }  
}
