/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.parametros.controller;

import gob.ypfb.fenix.parametros.service.obtener.IObtenerParametroService;
import gob.ypfb.fenix.parametros.service.obtener.ObtenerParametroRequest;
import gob.ypfb.fenix.parametros.service.obtener.ObtenerParametroResponse;
import gob.ypfb.fenix.parametros.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*", methods ={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api-fenix/v1")
public class ParametrosController {

    @Autowired
    private IObtenerParametroService parametroService;

    @GetMapping("/parametro/{tipo}")
    public ResponseEntity<?> index(@PathVariable String tipo) {
        ObtenerParametroRequest requestService=new ObtenerParametroRequest();
        requestService.setTipo(tipo);

        ObtenerParametroResponse response = parametroService.obtenerParametroPorTipo(requestService);

        return new ResponseEntity<>(
                new Response(true,
                        HttpStatus.OK.value(),
                        "ok",
                        response.getParametros()),
                HttpStatus.OK);
    }
}
