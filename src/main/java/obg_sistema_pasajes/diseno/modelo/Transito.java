package obg_sistema_pasajes.diseno.modelo;

import java.time.LocalDateTime;

public class Transito {
        private int id;
        private LocalDateTime fechaHora;

        public Transito() {}
        
        public Transito(LocalDateTime fechaHora) {
            this.fechaHora = fechaHora;
        }
        
}
