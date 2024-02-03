/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.empresa;

import gob.ypfb.fenix.gestion.controller.empresas.empresa.request.*;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.listar.IObtenerCisternasService;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.listar.ObtenerCisternasPorEmpresaRequest;
import gob.ypfb.fenix.gestion.service.empresas.cisterna.listar.ObtenerCisternasResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.crear.CrearEmpresaRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.crear.CrearEmpresaResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.crear.ICrearEmpresaService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularCisternas.DesvincularEmpresaCisternasRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularCisternas.DesvincularEmpresaCisternasResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularCisternas.IDesvincularEmpresaCisternasService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularEmpresas.DesvincularAsociacionEmpresasRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularEmpresas.DesvincularAsociacionEmpresasResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.desvincularEmpresas.IDesvincularAsociacionEmpresasService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.eliminar.EliminarEmpresaRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.eliminar.EliminarEmpresaResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.eliminar.IEliminarEmpresaService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.listar.IObtenerEmpresasService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.listar.ObtenerEmpresasRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.listar.ObtenerEmpresasResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.modificar.IModificarEmpresaService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.modificar.ModificarEmpresaRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.modificar.ModificarEmpresaResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.obtener.IObtenerEmpresaService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.obtener.ObtenerEmpresaRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.obtener.ObtenerEmpresaResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularCisternas.IVincularEmpresaCisternasService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularCisternas.VincularEmpresaCisternasRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularCisternas.VincularEmpresaCisternasResponse;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularEmpresas.IVincularAsociacionEmpresasService;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularEmpresas.VincularAsociacionEmpresasRequest;
import gob.ypfb.fenix.gestion.service.empresas.empresa.vincularEmpresas.VincularAsociacionEmpresasResponse;
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
@Tag(name="Servicio para empresas",description = "")
public class EmpresaController implements IEmpresaController {
    @Autowired
    private IObtenerEmpresasService obtenerEmpresasService;
    @Autowired
    private ICrearEmpresaService crearEmpresaService;
    @Autowired
    private IObtenerEmpresaService obtenerEmpresaService;
    @Autowired
    private IModificarEmpresaService modificarEmpresaService;
    @Autowired
    private IEliminarEmpresaService eliminarEmpresaService;
    @Autowired
    private IVincularEmpresaCisternasService vincularEmpresaCisternasService;
    @Autowired
    private IDesvincularEmpresaCisternasService dvincularEmpresaCisternasService;
    @Autowired
    private IVincularAsociacionEmpresasService vincularAsociacionEmpresasService;
    @Autowired
    private IDesvincularAsociacionEmpresasService desvincularAsociacionEmpresasService;
    @Autowired
    private IObtenerCisternasService obtenerCisternasService;

    @Override
    @PostMapping("/gestiones/empresa")
    public ResponseEntity<?> crearEmpresa(@Valid DatosCrearEmpresaRequest request, BindingResult resultadoValidacion) {
        CrearEmpresaResponse response=null;
        Response responseMensaje = new Response();
        CrearEmpresaRequest requestService=new CrearEmpresaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearCrearEmpresaRequest(request, requestService);

        try
        {
            response= crearEmpresaService.crear(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);
        
            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - crearEmpresa:",e);
        }
    }

    private void mapearCrearEmpresaRequest(DatosCrearEmpresaRequest request, CrearEmpresaRequest requestService) {
        requestService.setRazonSocial(request.getRazonSocial());
        requestService.setPais(request.getPais());
        requestService.setEsAsociacion(false);
        requestService.setTipoDocumento(request.getTipoDocumento());
        requestService.setNumeroDocumento(request.getNumeroDocumento());
        requestService.setTipoConstitucion(request.getTipoConstitucion());
        requestService.setEmail(request.getEmail());
        requestService.setDireccion(request.getDireccion());
        requestService.setSitioWeb(request.getSitioWeb());
        requestService.setTelefono(request.getTelefono());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setRepresentanteLegal(request.getRepresentanteLegal());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
    }

    @Override
    @GetMapping("/gestiones/empresa/{idEmpresa}")
    public ResponseEntity<?> obtenerEmpresaPorId(Long idEmpresa) {
        ObtenerEmpresaResponse response=null;
        Response responseMensaje = new Response();
        ObtenerEmpresaRequest requestService=new ObtenerEmpresaRequest();

        requestService.setId(idEmpresa);

        try
        {
            response=  obtenerEmpresaService.obtenerEmpresaPorId(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerEmpresaPorId:",e);
        }
    }

    @Override
    @PutMapping("/gestiones/empresa/{idEmpresa}")
    public ResponseEntity<?> modificarEmpresaPorId(Long idEmpresa, @Valid DatosCrearEmpresaRequest request, BindingResult resultadoValidacion) {
        ModificarEmpresaResponse response=null;
        Response responseMensaje = new Response();
        ModificarEmpresaRequest requestService=new ModificarEmpresaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearModificarEmpresaRequest(idEmpresa, request, requestService);

        try
        {
            response= modificarEmpresaService.modificar(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - modificarEmpresaPorId:",e);
        }
    }

    private void mapearModificarEmpresaRequest(Long idEmpresa, DatosCrearEmpresaRequest request, ModificarEmpresaRequest requestService) {
        requestService.setId(idEmpresa);
        requestService.setRazonSocial(request.getRazonSocial());
        requestService.setPais(request.getPais());
        requestService.setEsAsociacion(false);
        requestService.setTipoDocumento(request.getTipoDocumento());
        requestService.setNumeroDocumento(request.getNumeroDocumento());
        requestService.setTipoConstitucion(request.getTipoConstitucion());
        requestService.setEmail(request.getEmail());
        requestService.setDireccion(request.getDireccion());
        requestService.setSitioWeb(request.getSitioWeb());
        requestService.setTelefono(request.getTelefono());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setRepresentanteLegal(request.getRepresentanteLegal());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
    }

    @Override
    @DeleteMapping("/gestiones/empresa/{idEmpresa}")
    public ResponseEntity<?> eliminarEmpresaPorId(Long idEmpresa, String justificacion, String usuario) {
        EliminarEmpresaResponse response=null;
        Response responseMensaje = new Response();
        EliminarEmpresaRequest requestService=new EliminarEmpresaRequest();

        requestService.setId(idEmpresa);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try
        {
            response= eliminarEmpresaService.eliminarLogicamente(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarEmpresaPorId:",e);
        }
    }

    @Override
    @GetMapping("/gestiones/empresa")
    public ResponseEntity<?> obtenerEmpresas()
    {
        ObtenerEmpresasResponse response=null;
        Response responseMensaje = new Response();
        ObtenerEmpresasRequest requestService=new ObtenerEmpresasRequest();

        requestService.setEsAsociacion(false);

        try
        {
            response= obtenerEmpresasService.obtenerEmpresas(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerEmpresas:",e);
        }
    }

    @Override
    @PostMapping("/gestiones/asociacion")
    public ResponseEntity<?> crearAsociacion(@Valid DatosCrearAsociacionRequest request, BindingResult resultadoValidacion) {
        CrearEmpresaResponse response=null;
        Response responseMensaje = new Response();
        CrearEmpresaRequest requestService=new CrearEmpresaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearCrearAsociacionRequest(request, requestService);

        try
        {
            response= crearEmpresaService.crear(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - crearAsociacion:",e);
        }
    }

    private void mapearCrearAsociacionRequest(DatosCrearAsociacionRequest request, CrearEmpresaRequest requestService) {
        requestService.setRazonSocial(request.getRazonSocial());
        requestService.setPais(request.getPais());
        requestService.setEsAsociacion(true);
        requestService.setTipoDocumento(request.getTipoDocumento());
        requestService.setNumeroDocumento(request.getNumeroDocumento());
        requestService.setTipoConstitucion(request.getTipoConstitucion());
        requestService.setEmail(request.getEmail());
        requestService.setDireccion(request.getDireccion());
        requestService.setSitioWeb(request.getSitioWeb());
        requestService.setTelefono(request.getTelefono());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setRepresentanteLegal(request.getRepresentanteLegal());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
    }

    @Override
    @GetMapping("/gestiones/asociacion/{idAsociacion}")
    public ResponseEntity<?> obtenerAsociacionPorId(Long idAsociacion) {
        ObtenerEmpresaResponse response=null;
        Response responseMensaje = new Response();
        ObtenerEmpresaRequest requestService=new ObtenerEmpresaRequest();

        requestService.setId(idAsociacion);

        try
        {
            response=  obtenerEmpresaService.obtenerEmpresaPorId(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerAsociacionPorId:",e);
        }
    }

    @Override
    @PutMapping("/gestiones/asociacion/{idAsociacion}")
    public ResponseEntity<?> modificarAsociacionPorId(Long idAsociacion, @Valid DatosCrearAsociacionRequest request, BindingResult resultadoValidacion) {
        ModificarEmpresaResponse response=null;
        Response responseMensaje = new Response();
        ModificarEmpresaRequest requestService=new ModificarEmpresaRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearModificarAsociacionRequest(idAsociacion, request, requestService);

        try
        {
            response= modificarEmpresaService.modificar(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - modificarAsociacionPorId:",e);
        }
    }

    private void mapearModificarAsociacionRequest(Long idAsociacion, DatosCrearAsociacionRequest request, ModificarEmpresaRequest requestService) {
        requestService.setId(idAsociacion);
        requestService.setRazonSocial(request.getRazonSocial());
        requestService.setPais(request.getPais());
        requestService.setEsAsociacion(true);
        requestService.setTipoDocumento(request.getTipoDocumento());
        requestService.setNumeroDocumento(request.getNumeroDocumento());
        requestService.setTipoConstitucion(request.getTipoConstitucion());
        requestService.setEmail(request.getEmail());
        requestService.setDireccion(request.getDireccion());
        requestService.setSitioWeb(request.getSitioWeb());
        requestService.setTelefono(request.getTelefono());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setRepresentanteLegal(request.getRepresentanteLegal());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
    }

    @Override
    @DeleteMapping("/gestiones/asociacion/{idAsociacion}")
    public ResponseEntity<?> eliminarAsociacionPorId(Long idAsociacion, String justificacion, String usuario) {
        EliminarEmpresaResponse response=null;
        Response responseMensaje = new Response();
        EliminarEmpresaRequest requestService=new EliminarEmpresaRequest();

        requestService.setId(idAsociacion);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try
        {
            response= eliminarEmpresaService.eliminarLogicamente(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - eliminarAsociacionPorId:",e);
        }
    }

    @Override
    @GetMapping("/gestiones/asociacion")
    public ResponseEntity<?> obtenerAsociaciones()
    {
        ObtenerEmpresasResponse response=null;
        Response responseMensaje = new Response();

        ObtenerEmpresasRequest requestService=new ObtenerEmpresasRequest();
        requestService.setEsAsociacion(true);
        try
        {
            response= obtenerEmpresasService.obtenerEmpresas(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerAsociaciones:",e);
        }
    }
    //--

    @Override
    @PostMapping("/gestiones/empresa/cisterna")
    public ResponseEntity<?> vincularEmrpesaCisternas(@Valid DatosVincularEmpresaCisternasRequest request, BindingResult resultadoValidacion) {
        VincularEmpresaCisternasResponse response=null;
        Response responseMensaje = new Response();
        VincularEmpresaCisternasRequest requestService=new VincularEmpresaCisternasRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearVincularEmpresaCisternasRequest(request, requestService);

        try
        {
            response= vincularEmpresaCisternasService.vincular(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - adicionarEmrpesaCisternas:",e);
        }
    }

    private void mapearVincularEmpresaCisternasRequest(DatosVincularEmpresaCisternasRequest request, VincularEmpresaCisternasRequest requestService) {
        requestService.setIdEmpresa(request.getIdEmpresa());
        requestService.setIdCisternas(request.getIdCisternas());
        requestService.setMotivo(request.getMotivo());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
    }

    @Override
    @PutMapping("/gestiones/empresa/cisterna")
    public ResponseEntity<?> desvincularEmpresaCisternas(@Valid DatosDesvincularEmpresaCisternasRequest request, BindingResult resultadoValidacion) {
        DesvincularEmpresaCisternasResponse response=null;
        Response responseMensaje = new Response();
        DesvincularEmpresaCisternasRequest requestService=new DesvincularEmpresaCisternasRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearDesvincularEmpresaCisternas(request, requestService);

        try
        {
            response= dvincularEmpresaCisternasService.desvincular(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - desasociarEmpresaCisternas:",e);
        }
    }

    private void mapearDesvincularEmpresaCisternas(DatosDesvincularEmpresaCisternasRequest request, DesvincularEmpresaCisternasRequest requestService) {
        requestService.setIdEmpresa(request.getIdEmpresa());
        requestService.setIdCisternas(request.getIdCisternas());
        requestService.setMotivo(request.getMotivo());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setEliminado(true);
    }

    @Override
    @PostMapping("/gestiones/asociacion/empresa")
    public ResponseEntity<?> vincularAsociacionEmpresas(@Valid DatosVincularAsociacionEmpresasRequest request, BindingResult resultadoValidacion) {
        VincularAsociacionEmpresasResponse response=null;
        Response responseMensaje = new Response();
        VincularAsociacionEmpresasRequest requestService=new VincularAsociacionEmpresasRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearVincularAsociacionEmpresasRequest(request, requestService);

        try
        {
            response= vincularAsociacionEmpresasService.vincular(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - vincularAsociacionEmpresas:",e);
        }
    }

    private void mapearVincularAsociacionEmpresasRequest(DatosVincularAsociacionEmpresasRequest request, VincularAsociacionEmpresasRequest requestService) {
        requestService.setIdAsociacion(request.getIdAsociacion());
        requestService.setIdEmpresas(request.getIdEmpresas());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
    }

    @Override
    @PutMapping("/gestiones/asociacion/empresa")
    public ResponseEntity<?> desvincularAsociacionEmpresas(@Valid DatosDesvincularAsociacionEmpresasRequest request, BindingResult resultadoValidacion) {
        DesvincularAsociacionEmpresasResponse response=null;
        Response responseMensaje = new Response();
        DesvincularAsociacionEmpresasRequest requestService=new DesvincularAsociacionEmpresasRequest();

        if(resultadoValidacion.hasErrors())
        {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        mapearDesvincularAsociacionEmpresasRequest(request, requestService);

        try
        {
            response= desvincularAsociacionEmpresasService.desvincular(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - desvincularAsociacionEmpresas:",e);
        }
    }

    private void mapearDesvincularAsociacionEmpresasRequest(DatosDesvincularAsociacionEmpresasRequest request, DesvincularAsociacionEmpresasRequest requestService) {
        requestService.setIdAsociacion(request.getIdAsociacion());
        requestService.setIdEmpresas(request.getIdEmpresas());
        requestService.setFechaInicio(request.getFechaInicio());
        requestService.setFechaFin(request.getFechaFin());
        requestService.setUsuarioAplicacion(request.getUsuarioAplicacion());
        requestService.setJustificacion(request.getJustificacion());
        requestService.setIpCliente(request.getIpCliente());
        requestService.setHash(request.getHash());
        requestService.setEliminado(true);
    }

    @Override
    @GetMapping("/gestiones/empresa/{idEmpresa}/cisterna")
    public ResponseEntity<?> obtenerCisternasPorEmpresa(@PathVariable Long idEmpresa) {
        ObtenerCisternasResponse response=null;
        Response responseMensaje = new Response();
        ObtenerCisternasPorEmpresaRequest requestService=new ObtenerCisternasPorEmpresaRequest();

        requestService.setIdEmpresa(idEmpresa);

        try
        {
            response = obtenerCisternasService.obtenerCisternasPorEmpresa(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        }
        catch (Exception e){
            return responseMensaje.retornarExcepcion("servicio rest - obtenerCisternasPorEmpresa:",e);
        }
    }
}
