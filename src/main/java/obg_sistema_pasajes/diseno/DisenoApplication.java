package obg_sistema_pasajes.diseno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import obg_sistema_pasajes.diseno.modelo.DatosPrueba;

@SpringBootApplication
public class DisenoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisenoApplication.class, args);
		DatosPrueba.cargar(); 
	}

}	
