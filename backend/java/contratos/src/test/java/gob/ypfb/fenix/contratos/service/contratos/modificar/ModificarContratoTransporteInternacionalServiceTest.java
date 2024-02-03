package gob.ypfb.fenix.contratos.service.contratos.modificar;

import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.ICrearContratoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class ModificarContratoTransporteInternacionalServiceTestDeberia {

    @Autowired
    IModificarContratoService modificarContratoTransporteInternacionalService;

    @Autowired
    ContratoRepository contratoRepository;

    @Autowired
    ICrearContratoService crearContratoTransporteInternacionalService;

    @Test
    @Transactional
    void modificar_un_contrato() {
        CrearContratoRequest crearContratoRequest= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        //CrearContratoResponse crearContratoResponse= crearContratoTransporteInternacionalService.crear(crearContratoRequest);

//        ModificarContratoRequest request = new ModificarContratoRequest();
//        request.setId(crearContratoResponse.getId());
//        request.setIdTipoContrato(crearContratoResponse.getContrato().getIdTipoContrato());
//        request.setNumeroContrato(crearContratoResponse.getContrato().getNumeroContrato());
//        request.setGestionContrato(crearContratoResponse.getContrato().getGestionContrato());
//        request.setJsonContrato("[]");
//        request.setJustificacion("modificado");
//        request.setUsuarioAplicacion("vmod");
//        request.setEliminado(true);

        ModificarContratoRequest request = new ModificarContratoRequest();
        request.setId(41L);
        request.setIdTipoContrato(crearContratoRequest.getIdTipoContrato());
        request.setNumeroContrato(crearContratoRequest.getNumeroContrato());
        request.setGestionContrato(crearContratoRequest.getGestionContrato());
        request.setJsonContrato(crearContratoRequest.getJsonContrato());
        request.setJustificacion("modificadotest");
        request.setUsuarioAplicacion("vmod");
        request.setEliminado(false);

        ModificarContratoResponse response= modificarContratoTransporteInternacionalService.modificar(request);

        assertNotNull(response);


    }
}