package gob.ypfb.fenix.contratos.service.contratos.obtener;

import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.crear.ICrearContratoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class ObtenerContratoTransporteInternacionalServiceTestDeberia {

    @Autowired
    IObtenerContratoService obtenerContratoTransporteInternacionalService;

    @Autowired
    ICrearContratoService crearContratoTransporteInternacionalService;

    @Test
    @Transactional
    void obtener_un_contrato_por_id() {
        CrearContratoRequest crearContratoRequest= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        CrearContratoResponse crearContratoResponse= crearContratoTransporteInternacionalService.crear(crearContratoRequest);

        ObtenerContratoRequest request=new ObtenerContratoRequest();
//        request.setId(crearContratoResponse.getContrato().getId());
//        request.setTipoContrato(crearContratoResponse.getContrato().getIdTipoContrato());
//
//        ObtenerContratoResponse response= obtenerContratoTransporteInternacionalService.obtenerContratoPorId(request);
//        assertNotNull(response);
//        assertEquals(crearContratoResponse.getContrato().getId(), response.getContrato().getId());
    }

    @Test
    @Transactional
    void obtener_un_contrato_por_codigo() {
        CrearContratoRequest crearContratoRequest= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        CrearContratoResponse crearContratoResponse= crearContratoTransporteInternacionalService.crear(crearContratoRequest);

        ObtenerContratoRequest request=new ObtenerContratoRequest();
//        request.setTipoContrato(crearContratoResponse.getContrato().getIdTipoContrato());
//        request.setNumeroContrato(crearContratoResponse.getContrato().getNumeroContrato());
//        request.setGestionContrato(crearContratoResponse.getContrato().getGestionContrato());
        ObtenerContratoResponse response= obtenerContratoTransporteInternacionalService.obtenerContratoPorCodigo(request);

        assertNotNull(response);
        //assertEquals(crearContratoResponse.getContrato().getId(), response.getContrato().getId());
    }
}