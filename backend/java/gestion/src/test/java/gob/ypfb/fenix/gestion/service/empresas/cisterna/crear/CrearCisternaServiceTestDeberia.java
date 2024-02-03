package gob.ypfb.fenix.gestion.service.empresas.cisterna.crear;

import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.CrearCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.CrearCisternaResponse;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.ICrearCisternaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class CrearCisternaServiceTestDeberia {

    @Autowired
    ICrearCisternaService crearCisternaService;

    @Test
    @Transactional
    void crear_una_cisterna() {
        //Given
        CrearCisternaRequest request=CrearCisternaRequestMother.crear();
        //When
        CrearCisternaResponse response=crearCisternaService.crear(request);
        //Then
        assertNotNull(response);
        assertTrue(response.getId()!=null);
    }
}