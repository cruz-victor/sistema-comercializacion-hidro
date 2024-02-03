package gob.ypfb.fenix.contratos.service.contratos.eliminar;

import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.crear.ICrearContratoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class EliminarContratoTransporteInternacionalServiceTestDeberia {
    @Autowired
    IEliminarContratoService eliminarContratoTransporteInternacionalService;

    @Autowired
    ICrearContratoService crearContratoTransporteInternacionalService;


    @Test
    @Transactional
    void eliminar_un_contrato() {
        CrearContratoRequest request= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        //TODO: verificar que no exista el contrato mediante el campo id_tipo_contrato, numero_contrato y gestion_contrato
        CrearContratoResponse crearContratoResponse= crearContratoTransporteInternacionalService.crear(request);

//        EliminarContratoRequest requestEliminar=new EliminarContratoRequest();
//        requestEliminar.setTipoContrato(crearContratoResponse.getContrato().getIdTipoContrato());
//        requestEliminar.setEliminado(true);
//        requestEliminar.setJustificacion("Eliminacino de prueba");
//        requestEliminar.setUsuario("vcru");
//        requestEliminar.setId(crearContratoResponse.getContrato().getId());
//
//        EliminarContratoResponse response= eliminarContratoTransporteInternacionalService.eliminarLogicamente(requestEliminar);
//        assertNotNull(response);
//        assertTrue(response.getContrato().getEliminado());
//        assertTrue(response.getContrato().getId()!=null);
    }
}
