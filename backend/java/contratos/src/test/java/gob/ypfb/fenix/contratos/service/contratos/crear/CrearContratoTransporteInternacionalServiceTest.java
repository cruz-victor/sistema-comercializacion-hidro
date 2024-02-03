package gob.ypfb.fenix.contratos.service.contratos.crear;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class CrearContratoTransporteInternacionalServiceTestDeberia {

    @Autowired
    ICrearContratoService crearContratoTransporteInternacionalService;

    @Test
    @Transactional
    void crear_un_contrato() {
        CrearContratoRequest request=RequestMother.crear();
        //TODO: verificar que no exista el contrato mediante el campo id_tipo_contrato, numero_contrato y gestion_contrato
        CrearContratoResponse response= crearContratoTransporteInternacionalService.crear(request);
        assertNotNull(response);
//        assertTrue(response.getContrato().getId()>0);
    }

    @Test
    @Transactional
    void crear_un_contrato_con_codigo_existente() {
        CrearContratoRequest request=RequestMother.crear();
        CrearContratoResponse response= crearContratoTransporteInternacionalService.crear(request);

        //assertNotNull(response);
        assertThrows(RuntimeException.class, ()-> {
            CrearContratoResponse responseDuplicado= crearContratoTransporteInternacionalService.crear(request);
        });
    }
}