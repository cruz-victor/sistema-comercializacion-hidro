package gob.ypfb.fenix.contratos.service.contratos.totales;

import gob.ypfb.fenix.contratos.repository.ContratoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
class ObtenerTotalesContratoTransporteInternacionalServiceTestDeberia {

    @Autowired
    ObtenerTotalesContratoService obtenerTotalesContratoTransporteInternacionalService;

    @Autowired
    ContratoRepository contratoRepository;

    @Test
    void obtener_informacion_dasboard() {
        ObtenerTotalesContratoRequest request = RequestMother.crear();
        ObtenerTotalesContratoResponse response = obtenerTotalesContratoTransporteInternacionalService.obtenerTotalesPorTipoContrato(request);
        assertNotNull(response);
    }

    @Test
    void obtener_objecto_json_de_totales() {
        String response = contratoRepository.obtenerTotalesDashboard("transporte", "internacional", 2021);
        //assertTrue(response);
        System.out.println(response);
    }
}