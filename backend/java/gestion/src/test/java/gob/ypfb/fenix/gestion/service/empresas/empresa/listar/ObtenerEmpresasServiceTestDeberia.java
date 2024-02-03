package gob.ypfb.fenix.gestion.service.empresas.empresa.listar;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class ObtenerEmpresasServiceTestDeberia {

    @Autowired
    IObtenerEmpresasService obtenerEmpresasService;

    @Test
    void obtener_todas_las_empresas() {
        ObtenerEmpresasRequest requestService=new ObtenerEmpresasRequest();
        requestService.setEsAsociacion(false);

        ObtenerEmpresasResponse response= obtenerEmpresasService.obtenerEmpresas(requestService);
        assertNotNull(response);
        assertTrue(response.getEmpresas().size()>0);
    }
}