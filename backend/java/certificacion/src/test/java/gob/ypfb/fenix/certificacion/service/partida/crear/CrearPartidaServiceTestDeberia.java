package gob.ypfb.fenix.certificacion.service.partida.crear;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
class CrearPartidaServiceTestDeberia {

    @Autowired
    ICrearPartidaService crearPartidaService;

    @Test
    void crear_una_partida() {
        CrearPartidaRequest request=new CrearPartidaRequest();
        //request.setId(1L);
        request.setGestion(2021);
        request.setCategoria("RECOM");
        request.setNumeroPartida("123");
        request.setMontoActual(new BigDecimal(123));
        request.setMontoAprobado(new BigDecimal(123) );
        request.setMontoIncremento(new BigDecimal(123));
        request.setSaldo(new BigDecimal( 123.44));
        request.setRutaDocumentoAdjunto("C://misDocumentos");
        request.setFechaRegistro(LocalDate.now());
        request.setEliminado(false);
        request.setUsuarioAplicacion("vcruz");
        request.setJustificacion("prueba");
        request.setIpCliente("10.1.1.1");
        request.setHash("abc");

        crearPartidaService.crear(request);
    }
}