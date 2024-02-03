package gob.ypfb.fenix.gestion;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gestion", version = "1.0", description = "Endpoints Gestion - Sistema Integrado Fenix"))
public class GestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionApplication.class, args);
    }
}