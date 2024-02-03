/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.lotes.lote;

import gob.ypfb.fenix.gestion.service.lotes.lote.crear.conTramos.CrearLoteRequest;
import gob.ypfb.fenix.gestion.service.lotes.lote.crear.conTramos.CrearLoteResponse;
import gob.ypfb.fenix.gestion.service.lotes.lote.crear.conTramos.ICrearLoteService;
import gob.ypfb.fenix.gestion.service.lotes.lote.eliminar.EliminarLoteRequest;
import gob.ypfb.fenix.gestion.service.lotes.lote.eliminar.EliminarLoteResponse;
import gob.ypfb.fenix.gestion.service.lotes.lote.eliminar.IEliminarLoteService;
import gob.ypfb.fenix.gestion.service.lotes.lote.obtener.IObtenerLotesService;
import gob.ypfb.fenix.gestion.service.lotes.lote.obtener.ObtenerLotesRequest;
import gob.ypfb.fenix.gestion.service.lotes.lote.obtener.ObtenerLotesResponse;
import gob.ypfb.fenix.gestion.shared.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins="*", methods ={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api-fenix/v1")
@Slf4j
@Tag(name="Servicios para lotes",description = "")
public class LoteController implements ILoteController {
    @Autowired
    private IObtenerLotesService obtenerLotesService;
    @Autowired
    private ICrearLoteService crearLoteService;
    @Autowired
    private IEliminarLoteService eliminarLoteService;

    @Override
    @PostMapping("/gestiones/lote/{tipoLote}")
    public ResponseEntity<?> crearLoteConTramos(String tipoLote, @Valid DatosLoteListaTramosRequest request, BindingResult resultadoValidacion) {
        CrearLoteResponse response=null;
        Response responseMensaje = new Response();
        CrearLoteRequest requestService=new CrearLoteRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        requestService.setTipoLote(tipoLote);
        requestService.setLote(request.getLote());

        try
        {
            response= crearLoteService.crear(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - crearLoteConTramos:",e);
        }
    }

    @Override
    @DeleteMapping("/gestiones/lote/{idLote}")
    public ResponseEntity<?> eliminarLotePorId(Long idLote, String justificacion, String usuario) {
        EliminarLoteResponse response=null;
        Response responseMensaje = new Response();
        EliminarLoteRequest requestService=new EliminarLoteRequest();

        requestService.setId(idLote);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try
        {
            response= eliminarLoteService.eliminarLogicamente(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarLotePorId:",e);
        }
    }
    
    @Override
    @GetMapping("/gestiones/lote/{tipoLote}/{gestion}")
    public ResponseEntity<?> obtenerLotesPorTipo(String tipoLote, Integer gestion){
        ObtenerLotesResponse response=null;
        Response responseMensaje = new Response();
        ObtenerLotesRequest requestService=new ObtenerLotesRequest();

        requestService.setTipoLote(tipoLote);
        requestService.setGestion(gestion);

        try
        {
            response = obtenerLotesService.obtenerLotesPorTipo(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerLotesPorTipo:",e);
        }
    }
}