/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos;

import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import gob.ypfb.fenix.contratos.repository.VolumenEjecutadoRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Contratos", version = "1.0", description = "Endpoints Contratos - Sistema Integrado Fenix"))
public class ContratosApplication {

    @Autowired
    ContratoRepository repository;

    @Autowired
    VolumenEjecutadoRepository movimientoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ContratosApplication.class, args);
    }
}
