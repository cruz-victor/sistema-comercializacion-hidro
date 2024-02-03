package gob.ypfb.fenix.contratos.service.contratos.listar;

import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.crear.ICrearContratoService;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.IObtenerContratosPorTipoService;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.ObtenerContratosPorTipoRequest;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.ObtenerContratosPorTipoResponse;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class ObtenerTodosContratosTransporteInternacionalServiceTestDeberia {

    @Autowired
    IObtenerContratosPorTipoService obtenerTodosContratosTransporteInternacionalService;

    @Autowired
    ICrearContratoService crearContratoTransporteInternacionalService;
    
    @Test
    @Transactional
    void obtener_todos_los_contratos() {
        CrearContratoRequest CrearContratoRequest= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        CrearContratoResponse CreaContratoResponse= crearContratoTransporteInternacionalService.crear(CrearContratoRequest);

        ObtenerContratosPorTipoRequest request=RequestMother.crear();
        ObtenerContratosPorTipoResponse response= obtenerTodosContratosTransporteInternacionalService.obtenerContratos(request);
        //log.info("----->"+response.getContratos().toString());
        assertNotNull(response);
        assertTrue(response.getContratos().size()>0);
    }

    @Test
    @Transactional
    void obtener_todos_los_contratos_por_gestion() {
        CrearContratoRequest CrearContratoRequest= gob.ypfb.fenix.contratos.service.contratos.crear.RequestMother.crear();
        CrearContratoResponse CreaContratoResponse= crearContratoTransporteInternacionalService.crear(CrearContratoRequest);

        ObtenerContratosPorTipoRequest request=RequestMother.crear();
        ObtenerContratosPorTipoResponse response= obtenerTodosContratosTransporteInternacionalService.obtenerContratosGestion(request);

        //List<Integer> fechas = obtenerFechasDeLosContratosRecuperados(response);

        assertNotNull(response);
        //assertTrue(fechas.size()==1);
        //assertEquals(request.getGestionContrato(), fechas.get(0));
    }

    private List<Integer> obtenerFechasDeLosContratosRecuperados(ObtenerContratosPorTipoResponse response) {
        String contratosJson= response.getContratos().toString();

        InputStream inputStream=new ByteArrayInputStream(contratosJson.getBytes(StandardCharsets.UTF_8));
        JsonReader reader= Json.createReader(inputStream);

        JsonArray contratosJsonArray=reader.readArray();

        DateTimeFormatter formato=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Integer> fechas=contratosJsonArray.getValuesAs(JsonObject.class).stream()
                .map(contrato->contrato.get("/informacionGeneral/gestion"))
                .map(contrato->contrato.toString())
                .map(fecha-> LocalDate.parse(fecha.replace("\"",""), formato))
                .map(fecha->fecha.getYear())
                .distinct()
                .collect(Collectors.toList());
        return fechas;
    }
}