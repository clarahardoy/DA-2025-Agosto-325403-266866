package obg_sistema_pasajes.diseno;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import obg_sistema_pasajes.diseno.exception.PeajeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PeajeException.class)
    public ResponseEntity<String> manejarExcepcion(PeajeException e) {
        //299 porque se produjo curso alternativo
        //endpoints tienen que tirar excepciones de tipo PeajeException
        // return ResponseEntity.status(299).body("Error: " + e.getMessage()); ------------------------------------------------------------
        // Devolver sólo el mensaje para que la vista muestre exactamente el texto esperado
        return ResponseEntity.status(299).body(e.getMessage());
    }
}