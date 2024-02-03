/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.certificacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import gob.ypfb.fenix.certificacion.service.certificacion.crear.CrearCertificacionRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.crear.CrearCertificacionResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.crear.ICrearCertificacionService;
import gob.ypfb.fenix.certificacion.service.certificacion.eliminar.EliminarCertificacionRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.eliminar.EliminarCertificacionResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.eliminar.IEliminarCertificacionService;
import gob.ypfb.fenix.certificacion.service.certificacion.listar.porConcepto.IObtenerCertificacionesService;
import gob.ypfb.fenix.certificacion.service.certificacion.listar.porConcepto.ObtenerCertificacionesRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.listar.porConcepto.ObtenerCertificacionesResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.listar.todo.IObtenerTodasCertificacionesService;
import gob.ypfb.fenix.certificacion.service.certificacion.listar.todo.ObtenerTodasCertificacionesResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.modificar.IModificarCertificacionService;
import gob.ypfb.fenix.certificacion.service.certificacion.modificar.ModificarCertificacionRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.modificar.ModificarCertificacionResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.obtener.IObtenerCertificacionService;
import gob.ypfb.fenix.certificacion.service.certificacion.obtener.ObtenerCertificacionRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.obtener.ObtenerCertificacionResponse;
import gob.ypfb.fenix.certificacion.service.certificacion.totales.IObtenerTotalesCertificacionService;
import gob.ypfb.fenix.certificacion.service.certificacion.totales.ObtenerTotalesCertificacionRequest;
import gob.ypfb.fenix.certificacion.service.certificacion.totales.ObtenerTotalesCertificacionResponse;
import gob.ypfb.fenix.certificacion.shared.Response;
import gob.ypfb.fenix.certificacion.shared.Utilidades;
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
@Tag(name="Servicios para Certificaciones",description = "")
public class CertificacionController implements ICertificacionController {
    @Autowired
    private IObtenerCertificacionService obtenerCertificacionService;
    @Autowired
    private IObtenerCertificacionesService obtenerCertificacionesService;
    @Autowired
    private IObtenerTodasCertificacionesService obtenerTodasCertificacionesService;
    @Autowired
    private ICrearCertificacionService crearCertificacionService;
    @Autowired
    private IModificarCertificacionService modificarCertificacionService;
    @Autowired
    private IEliminarCertificacionService eliminarCertificacionService;
    @Autowired
    private IObtenerTotalesCertificacionService obtenerTotalesCertificacionService;

    @PostMapping("/certificacionPresupuestaria/partida/certificacion")
    public ResponseEntity<?> crearCertificacion(@Valid DatosCrearCertificacionRequest request, BindingResult resultadoValidacion) {
        CrearCertificacionResponse response=null;
        Response responseMensaje = new Response();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        establecerIdPartidaIdCertificacionNull(request);

        CrearCertificacionRequest requestService = mapearParametrosCrearRequest(request);

        try
        {
            response= crearCertificacionService.crear(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - crearCertificacion:", e);
        }
    }

    private CrearCertificacionRequest mapearParametrosCrearRequest(DatosCrearCertificacionRequest request) {
        CrearCertificacionRequest requestService= new CrearCertificacionRequest();
        requestService.setIdPartida(request.getIdPartida());
        requestService.setConcepto(request.getConcepto());
        requestService.setFechaEmision(request.getFechaEmision());
        requestService.setNumeroCertificacion(request.getNumeroCertificacion());
        requestService.setDescripcion(request.getDescripcion());
        requestService.setMontoMaximo(request.getMontoMaximo());
        requestService.setMontoEjecutado(request.getMontoEjecutado());
        requestService.setSaldo(request.getSaldo());
        requestService.setTipoServicio(request.getTipoServicio());
        requestService.setIdProcesoContratacion(request.getIdProcesoContratacion());
        requestService.setIdLotes(Utilidades.convertirAjson(request.getIdLotes()));
        requestService.setIdContrato(request.getIdContrato());
        requestService.setRutaDocumentoAdjunto(request.getRutaDocumentoAdjunto());
        requestService.setFechaRegistro(LocalDate.now());
        requestService.setEliminado(false);
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setIdCertificacion(request.getIdCertificacion());
        return requestService;
    }

    private void establecerIdPartidaIdCertificacionNull(DatosCrearCertificacionRequest request) {
        if (request.getIdPartida()==0){
            request.setIdPartida(null);
        }

        if (request.getIdCertificacion()==0){
            request.setIdCertificacion(null);
        }
    }

    @GetMapping("/certificacionPresupuestaria/partida/certificacion/{idCertificacion}")
    public ResponseEntity<?> obtenerCertificacionPorId(Long idCertificacion) {
        ObtenerCertificacionResponse response=null;
        Response responseMensaje = new Response();

        ObtenerCertificacionRequest requestService=new ObtenerCertificacionRequest();
        requestService.setId(idCertificacion);

        try
        {
            response=  obtenerCertificacionService.obtenerCertificacionPorId(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCertificacionPorId:",e);
        }
    }

    @PutMapping("/certificacionPresupuestaria/partida/certificacion/{idCertificacion}")
    public ResponseEntity<?> modificarCertificacionPorId(Long idCertificacion, @Valid DatosCrearCertificacionRequest request,BindingResult resultadoValidacion) {
        ModificarCertificacionResponse response=null;
        Response responseMensaje = new Response();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }
        establecerIdPartidaIdCertificacionNull(request);

        ModificarCertificacionRequest requestService = mapearParametrosModificarRequest(idCertificacion, request);

        try
        {
            response= modificarCertificacionService.modificar(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - modificarCertificacionPorId:",e);
        }
    }

    private ModificarCertificacionRequest mapearParametrosModificarRequest(Long idCertificacion, DatosCrearCertificacionRequest request) {
        ModificarCertificacionRequest requestService= new ModificarCertificacionRequest();

        requestService.setId(idCertificacion);
        requestService.setIdPartida(request.getIdPartida());
        requestService.setConcepto(request.getConcepto());
        requestService.setFechaEmision(request.getFechaEmision());
        requestService.setNumeroCertificacion(request.getNumeroCertificacion());
        requestService.setDescripcion(request.getDescripcion());
        requestService.setMontoMaximo(request.getMontoMaximo());
        requestService.setMontoEjecutado(request.getMontoEjecutado());
        requestService.setSaldo(request.getSaldo());
        requestService.setTipoServicio(request.getTipoServicio());
        requestService.setIdProcesoContratacion(requestService.getIdProcesoContratacion());
        requestService.setIdLotes(Utilidades.convertirAjson(request.getIdLotes()));
        requestService.setIdContrato(request.getIdContrato());
        requestService.setRutaDocumentoAdjunto(request.getRutaDocumentoAdjunto());
        requestService.setFechaRegistro(LocalDate.now());
        requestService.setEliminado(false);
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setIdCertificacion(request.getIdCertificacion());
        return requestService;
    }

    @DeleteMapping("/certificacionPresupuestaria/partida/certificacion/{idCertificacion}")
    public ResponseEntity<?> eliminarCertificacionPorId(Long idCertificacion,  String justificacion, String usuario) {
        EliminarCertificacionResponse response=null;
        Response responseMensaje = new Response();

        EliminarCertificacionRequest requestService = mapearParametrosEliminarRequest(idCertificacion, justificacion, usuario);

        try
        {
            response= eliminarCertificacionService.eliminarLogicamente(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarCertificacionPorId:",e);
        }
    }

    private EliminarCertificacionRequest mapearParametrosEliminarRequest(Long idCertificacion, String justificacion, String usuario) {
        EliminarCertificacionRequest requestService=new EliminarCertificacionRequest();
        requestService.setId(idCertificacion);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);
        return requestService;
    }

    @GetMapping("/certificacionPresupuestaria/partida/certificacion/concepto/{concepto}")
    public ResponseEntity<?> obtenerCertificacionesPorTipoCertificacion(String concepto)
    {
        ObtenerCertificacionesResponse response=null;
        Response responseMensaje = new Response();

        ObtenerCertificacionesRequest requestService=new ObtenerCertificacionesRequest();
        requestService.setConcepto(concepto);

        try
        {
            response= obtenerCertificacionesService.obtenerCertificaciones(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCertificacionesPorTipoCertificacion:",e);
        }
    }

    @GetMapping("/certificacionPresupuestaria/partida/certificacion")
    public ResponseEntity<?> obtenerCertificaciones()
    {
        ObtenerTodasCertificacionesResponse response=null;
        Response responseMensaje = new Response();

        try
        {
            response= obtenerTodasCertificacionesService.obtenerCertificaciones();

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCertificaciones:",e);
        }
    }

    @Override
    @GetMapping("/certificacionPresupuestaria/partida/certificacion/totales/{gestion}")
    public ResponseEntity<?> obtenerTotalesCertificaciones(Integer gestion) throws JsonProcessingException {
        ObtenerTotalesCertificacionResponse response=null;
        Response responseMensaje = new Response();
        ObtenerTotalesCertificacionRequest requestService=new ObtenerTotalesCertificacionRequest();
        requestService.setGestion(gestion);

        try
        {
            response= obtenerTotalesCertificacionService.obtenerTotales(requestService);

            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getTotalesDashboardObjeto());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerTotalesCertificaciones:",e);
        }
    }
}