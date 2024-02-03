/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.cisterna;

import gob.ypfb.fenix.gestion.controller.empresas.cisterna.request.DatosCrearCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.CrearCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.CrearCisternaResponse;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.unaCisterna.ICrearCisternaService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.crear.variasCisternas.ICrearCisternasService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.eliminar.EliminarCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.eliminar.EliminarCisternaResponse;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.eliminar.IEliminarCisternaService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.listar.IObtenerCisternasService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.listar.ObtenerCisternasResponse;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.modificar.IModificarCisternaService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.modificar.ModificarCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.modificar.ModificarCisternaResponse;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.obtener.IObtenerCisternaService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.obtener.ObtenerCisternaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.obtener.ObtenerCisternaResponse;
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
@Tag(name="Servicios para cisternas",description = "")
public class CisternaController implements ICisternaController {
    @Autowired
    private IObtenerCisternasService obtenerCisternasService;
    @Autowired
    private ICrearCisternaService crearCisternaService;
    @Autowired
    private ICrearCisternasService crearCisternasService;
    @Autowired
    private IObtenerCisternaService obtenerCisternaService;
    @Autowired
    private IModificarCisternaService modificarCisternaService;
    @Autowired
    private IEliminarCisternaService eliminarCisternaService;

    @Override
    @PostMapping("/gestiones/cisterna")
    public ResponseEntity<?> crearCisterna(@Valid DatosCrearCisternaRequest request, BindingResult resultadoValidacion) {
        CrearCisternaResponse response=null;
        Response responseMensaje = new Response();
        CrearCisternaRequest requestService=new CrearCisternaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearCrearCisternaRequest(request, requestService);

        try
        {
            response= crearCisternaService.crear(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - crearCisterna:",e);
        }
    }

    private void mapearCrearCisternaRequest(DatosCrearCisternaRequest request, CrearCisternaRequest requestService) {
        requestService.setAnio(request.getAnio());
        requestService.setPlaca(request.getPlaca());
        requestService.setMarca(request.getMarca());
        requestService.setColor(request.getColor());
        requestService.setCapacidad(request.getCapacidad());
        requestService.setNroCompartimientos(request.getNroCompartimientos());
        requestService.setNroChasis(request.getNroChasis());
        requestService.setDisposicionEjes(request.getDisposicionEjes());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
    }

    @Override
    @GetMapping("/gestiones/cisterna/{idCisterna}")
    public ResponseEntity<?> obtenerCisternaPorId(Long idCisterna)
    {
        ObtenerCisternaResponse response=null;
        Response responseMensaje = new Response();
        ObtenerCisternaRequest requestService=new ObtenerCisternaRequest();

        requestService.setId(idCisterna);

        try
        {
            response= obtenerCisternaService.obtenerCisternaPorId(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCisternaPorId:",e);
        }
    }

    @Override
    @PutMapping("/gestiones/cisterna/{idCisterna}")
    public ResponseEntity<?> modificarCisternaPorId(Long idCisterna, @Valid DatosCrearCisternaRequest request, BindingResult resultadoValidacion) {
        ModificarCisternaResponse response=null;
        Response responseMensaje = new Response();
        ModificarCisternaRequest requestService=new ModificarCisternaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearModificarCisternaRequest(idCisterna, request, requestService);

        try
        {
            response= modificarCisternaService.modificar(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - modificarCisternaPorId:",e);
        }
    }

    private void mapearModificarCisternaRequest(Long idCisterna, DatosCrearCisternaRequest request, ModificarCisternaRequest requestService) {
        requestService.setId(idCisterna);
        requestService.setAnio(request.getAnio());
        requestService.setPlaca(request.getPlaca());
        requestService.setMarca(request.getMarca());
        requestService.setColor(request.getColor());
        requestService.setCapacidad(request.getCapacidad());
        requestService.setNroCompartimientos(request.getNroCompartimientos());
        requestService.setNroChasis(request.getNroChasis());
        requestService.setDisposicionEjes(request.getDisposicionEjes());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
    }

    @Override
    @DeleteMapping("/gestiones/cisterna/{idCisterna}")
    public ResponseEntity<?> eliminarCisternaPorId(Long idCisterna, String justificacion, String usuario) {
        EliminarCisternaResponse response=null;
        Response responseMensaje = new Response();
        EliminarCisternaRequest requestService=new EliminarCisternaRequest();

        requestService.setId(idCisterna);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try
        {
            response= eliminarCisternaService.eliminarLogicamente(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarCisternaPorId:",e);
        }
    }

    @Override
    @GetMapping("/gestiones/cisterna")
    public ResponseEntity<?> obtenerCisternas() {
        ObtenerCisternasResponse response=null;
        Response responseMensaje = new Response();

        try
        {
            response= obtenerCisternasService.obtenerCisternas();
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
