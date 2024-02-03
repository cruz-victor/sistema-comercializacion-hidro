/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.lotes.tramo;

import gob.ypfb.fenix.gestion.service.lotes.tramo.obtener.IObtenerTramosService;
import gob.ypfb.fenix.gestion.service.lotes.tramo.obtener.ObtenerTramosPorLoteRequest;
import gob.ypfb.fenix.gestion.service.lotes.tramo.obtener.ObtenerTramosResponse;
import gob.ypfb.fenix.gestion.shared.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", methods ={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api-fenix/v1")
@Slf4j
@Tag(name="Servicios para lotes",description = "")
public class TramoController implements ITramoController {
    @Autowired
    private IObtenerTramosService obtenerTramosService;

    @Override
    @GetMapping("/gestiones/lote/{idLote}/tramo")
    public ResponseEntity<?> obtenerTramosPorLote(@PathVariable Long idLote){
        ObtenerTramosResponse response=null;
        Response responseMensaje = new Response();
        ObtenerTramosPorLoteRequest requestService=new ObtenerTramosPorLoteRequest();

        requestService.setIdLote(idLote);

        try
        {
            response = obtenerTramosService.obtenerTramosPorLote(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerTramosPorLote:",e);
        }
    }
}
