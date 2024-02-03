/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.partida;

import com.fasterxml.jackson.core.JsonProcessingException;
import gob.ypfb.fenix.certificacion.service.partida.crear.CrearPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.crear.CrearPartidaResponse;
import gob.ypfb.fenix.certificacion.service.partida.crear.ICrearPartidaService;
import gob.ypfb.fenix.certificacion.service.partida.eliminar.EliminarPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.eliminar.EliminarPartidaResponse;
import gob.ypfb.fenix.certificacion.service.partida.eliminar.IEliminarPartidaService;
import gob.ypfb.fenix.certificacion.service.partida.listar.IObtenerPartidasService;
import gob.ypfb.fenix.certificacion.service.partida.listar.ObtenerPartidasResponse;
import gob.ypfb.fenix.certificacion.service.partida.modificar.IModificarPartidaService;
import gob.ypfb.fenix.certificacion.service.partida.modificar.ModificarPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.modificar.ModificarPartidaResponse;
import gob.ypfb.fenix.certificacion.service.partida.obtener.IObtenerPartidaService;
import gob.ypfb.fenix.certificacion.service.partida.obtener.ObtenerPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.obtener.ObtenerPartidaResponse;
import gob.ypfb.fenix.certificacion.service.partida.totales.IObtenerTotalesPartidaService;
import gob.ypfb.fenix.certificacion.service.partida.totales.ObtenerTotalesPartidaRequest;
import gob.ypfb.fenix.certificacion.service.partida.totales.ObtenerTotalesPartidaResponse;
import gob.ypfb.fenix.certificacion.shared.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins="*", methods ={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping("/api-fenix/v1")
@Slf4j
@Tag(name="Servicios para Partidas",description = "")
public class PartidaController implements IPartidaController {
    @Autowired
    private IObtenerPartidaService obtenerPartidaService;
    @Autowired
    private IObtenerPartidasService obtenerPartidasService;
    @Autowired
    private ICrearPartidaService crearPartidaService;
    @Autowired
    private IModificarPartidaService modificarPartidaService;
    @Autowired
    private IEliminarPartidaService eliminarPartidaService;
    @Autowired
    private IObtenerTotalesPartidaService obtenerTotalesPartidaService;

    @PostMapping("/PartidaPresupuestaria/partida")
    public ResponseEntity<?> crearPartida(@Valid DatosCrearPartidaRequest request, BindingResult resultadoValidacion) {
        CrearPartidaResponse response=null;
        Response responseMensaje = new Response();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        CrearPartidaRequest requestService= IPartidaControllerMapper.INSTANCIA.requestToCrearPartidaRequest(request);
        requestService.setFechaRegistro(LocalDate.now());
        requestService.setEliminado(false);

        try
        {
            response= crearPartidaService.crear(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - crearPartida:",e);
        }
    }

    @GetMapping("/PartidaPresupuestaria/partida/{idPartida}")
    public ResponseEntity<?> obtenerPartidaPorId(Long idPartida)
    {
        ObtenerPartidaResponse response=null;
        Response responseMensaje = new Response();
        ObtenerPartidaRequest requestService=new ObtenerPartidaRequest();

        requestService.setId(idPartida);

        try
        {
            response= obtenerPartidaService.obtenerPartidaPorId(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerPartidaPorId:",e);
        }
    }


    @PutMapping("/PartidaPresupuestaria/partida/{idPartida}")
    public ResponseEntity<?> modificarPartidaPorId(Long idPartida, @Valid DatosCrearPartidaRequest request, BindingResult resultadoValidacion) {
        ModificarPartidaResponse response=null;
        Response responseMensaje = new Response();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        ModificarPartidaRequest requestService= IPartidaControllerMapper.INSTANCIA.requestToModificarPartidaRequest(request);
        requestService.setId(idPartida);
        requestService.setFechaRegistro(LocalDate.now());
        requestService.setEliminado(false);

        try
        {
            response= modificarPartidaService.modificar(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - modificarPartidaPorId:",e);
        }
    }


    @DeleteMapping("/PartidaPresupuestaria/partida/{idPartida}")
    public ResponseEntity<?> eliminarPartidaPorId(Long idPartida, String justificacion, String usuario) {
        EliminarPartidaResponse response=null;
        Response responseMensaje = new Response();
        EliminarPartidaRequest requestService=new EliminarPartidaRequest();

        requestService.setId(idPartida);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try
        {
            response= eliminarPartidaService.eliminarLogicamente(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarPartidaPorId:",e);
        }
    }

    @GetMapping("/PartidaPresupuestaria/partida")
    public ResponseEntity<?> obtenerPartidas() {
        ObtenerPartidasResponse response=null;
        Response responseMensaje = new Response();

        try
        {
            response= obtenerPartidasService.obtenerPartidas();

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerPartidas:",e);
        }
    }

    @Override
    @GetMapping("/PartidaPresupuestaria/partida/totales/{gestion}")
    public ResponseEntity<?> obtenerTotalesPartidas(Integer gestion) throws JsonProcessingException  {
        ObtenerTotalesPartidaResponse response=null;
        Response responseMensaje = new Response();
        ObtenerTotalesPartidaRequest requestService=new ObtenerTotalesPartidaRequest();
        requestService.setGestion(gestion);

        try
        {
            response= obtenerTotalesPartidaService.obtenerTotales(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getTotalesDashboardObjeto());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerTotalesPartidas:",e);
        }
    }
}
