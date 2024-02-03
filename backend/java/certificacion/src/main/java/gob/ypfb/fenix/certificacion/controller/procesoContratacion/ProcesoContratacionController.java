/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.procesoContratacion;

import gob.ypfb.fenix.certificacion.service.procesoContratacion.listar.IObtenerProcesosService;
import gob.ypfb.fenix.certificacion.service.procesoContratacion.listar.ObtenerProcesosResponse;
import gob.ypfb.fenix.certificacion.shared.Response;
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
@Tag(name="Servicios para Procesos de contratacion",description = "")
public class ProcesoContratacionController implements IProcesoContratacionController {
    
    @Autowired
    private IObtenerProcesosService obtenerProcesosService;

    @GetMapping("/procesoContratacion")
    public ResponseEntity<?> obtenerProcesosPorGestion() {
        ObtenerProcesosResponse response=null;
        Response responseMensaje = new Response();

        try
        {
            response= obtenerProcesosService.obtenerProcesos();

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCisternas:",e);
        }
    }
}
