package gob.ypfb.fenix.gestion.service.empresas.cisterna.listar;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class ObtenerCisternasServiceTestDeberia {

    @Autowired
    IObtenerCisternasService obtenerCisternasService;

    @Test
    void obtener_todas_las_cisternas() {
        ObtenerCisternasResponse response=obtenerCisternasService.obtenerCisternas();
        assertNotNull(response);
        assertTrue(response.getCisternas().size()>0);

    }
}