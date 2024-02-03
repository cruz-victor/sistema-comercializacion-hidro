/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.nacional;

import gob.ypfb.fenix.contratos.controller.contratos.transporte.nacional.request.DatosCrearContratoTransporteNacionalRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.crear.CrearContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.crear.ICrearContratoService;
import gob.ypfb.fenix.contratos.service.contratos.eliminar.EliminarContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.eliminar.EliminarContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.eliminar.IEliminarContratoService;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.IObtenerContratosPorTipoService;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.ObtenerContratosPorTipoRequest;
import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.ObtenerContratosPorTipoResponse;
import gob.ypfb.fenix.contratos.service.contratos.modificar.IModificarContratoService;
import gob.ypfb.fenix.contratos.service.contratos.modificar.ModificarContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.modificar.ModificarContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.obtener.IObtenerContratoService;
import gob.ypfb.fenix.contratos.service.contratos.obtener.ObtenerContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.obtener.ObtenerContratoResponse;
import gob.ypfb.fenix.contratos.service.contratos.totales.IObtenerTotalesContratoService;
import gob.ypfb.fenix.contratos.service.contratos.totales.ObtenerTotalesContratoRequest;
import gob.ypfb.fenix.contratos.service.contratos.totales.ObtenerTotalesContratoResponse;
import gob.ypfb.fenix.contratos.shared.Parametro;
import gob.ypfb.fenix.contratos.shared.Response;
import gob.ypfb.fenix.contratos.shared.Utilidades;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RestController
@RequestMapping("/api-fenix/v1")
@Slf4j
@Tag(name = "Servicio para los Contratos de Transporte Nacional", description = "")
public class ContratosTransporteNacionalController implements IContratosTransporteNacionalController {
    Response responseMensaje;

    @Autowired
    private ICrearContratoService crearContratoService;
    @Autowired
    private IObtenerContratoService obtenerContratoService;
    @Autowired
    private IObtenerContratosPorTipoService obtenerTodosContratosService;
    @Autowired
    private IModificarContratoService modificarContratoService;
    @Autowired
    private IEliminarContratoService eliminarContratoService;
    @Autowired
    private IObtenerTotalesContratoService obtenerTotalesContratoService;

    @PostMapping("/contratos/transporte/nacional")
    public ResponseEntity<?> crearContratoTransporteNacional(@Valid DatosCrearContratoTransporteNacionalRequest request, BindingResult resultadoValidacion) {
        CrearContratoRequest requestServicio = new CrearContratoRequest();
        CrearContratoResponse response = null;
        responseMensaje = new Response();

        if (resultadoValidacion.hasErrors()) {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        requestServicio.setIdTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestServicio.setNumeroContrato(request.getInformacionGeneral().getNumeroContrato());
        requestServicio.setGestionContrato(request.getInformacionGeneral().getGestion());
        requestServicio.setUsuarioAplicacion(request.getInformacionUsuario().getUsuario());
        requestServicio.setIpCliente(request.getInformacionUsuario().getIp());
        requestServicio.setJsonContrato(new Utilidades<DatosCrearContratoTransporteNacionalRequest>().convertirContratoAjson(request));

        try {
            response = crearContratoService.crear(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - crearContratoTransportenacional:", e);
        }
    }

    @GetMapping("/contratos/transporte/nacional/{idContrato}")
    public ResponseEntity<?> obtenerContratoTranporteNacionalPorId(Long idContrato) {
        ObtenerContratoRequest requestServicio = new ObtenerContratoRequest();
        ObtenerContratoResponse response = null;
        responseMensaje = new Response();

        requestServicio.setTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestServicio.setId(idContrato);

        try {
            response = obtenerContratoService.obtenerContratoPorId(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getContrato());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - obtenerContratoTranportenacionalPorId:", e);
        }
    }

    @GetMapping(value = "/contratos/transporte/nacional/codigo")
    public ResponseEntity<?> obtenerContratoTranporteNacionalPorCodigo(Integer numeroContraro, Integer gestionContrato) {
        ObtenerContratoRequest requestServicio = new ObtenerContratoRequest();
        ObtenerContratoResponse response = null;
        responseMensaje = new Response();

        requestServicio.setTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestServicio.setNumeroContrato(numeroContraro);
        requestServicio.setGestionContrato(gestionContrato);

        try {
            response = obtenerContratoService.obtenerContratoPorCodigo(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getContrato());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - obtenerContratoTranportenacionalPorCodigo:", e);
        }
    }

    @PutMapping(value = "/contratos/transporte/nacional/{idContrato}")
    public ResponseEntity<?> modificarContratoTranporteNacionalPorId(Long idContrato, String justificacion,@Valid DatosCrearContratoTransporteNacionalRequest request, BindingResult resultadoValidacion) {
        ModificarContratoRequest requestServicio = new ModificarContratoRequest();
        ModificarContratoResponse response = null;
        responseMensaje = new Response();

        if (resultadoValidacion.hasErrors()) {
            return responseMensaje.retornarErroresValidacion(resultadoValidacion);
        }

        requestServicio.setId(idContrato);
        requestServicio.setIdTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestServicio.setNumeroContrato(request.getInformacionGeneral().getNumeroContrato());
        requestServicio.setGestionContrato(request.getInformacionGeneral().getGestion());
        requestServicio.setUsuarioAplicacion(request.getInformacionUsuario().getUsuario());
        requestServicio.setIpCliente(request.getInformacionUsuario().getIp());
        requestServicio.setJustificacion(justificacion);
        requestServicio.setJsonContrato(new Utilidades<DatosCrearContratoTransporteNacionalRequest>().convertirContratoAjson(request));

        try {
            response = modificarContratoService.modificar(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - modificarContratoTranportenacionalPorId:", e);
        }
    }

    @DeleteMapping("/contratos/transporte/nacional/{idContrato}")
    public ResponseEntity<?> eliminarContratoTranporteNacionalPorId(Long idContrato, String justificacion, String usuario) {
        EliminarContratoRequest requestService = new EliminarContratoRequest();
        EliminarContratoResponse response = null;
        responseMensaje = new Response();

        requestService.setId(idContrato);
        requestService.setTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestService.setJustificacion(justificacion);
        requestService.setUsuario(usuario);
        requestService.setEliminado(true);

        try {
            response = eliminarContratoService.eliminarLogicamente(requestService);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response);

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - eliminarContratoTranportenacionalPorId:", e);
        }
    }

    @GetMapping("/contratos/transporte/nacional")
    public ResponseEntity<?> obtenerContratosTranporteNacional() {
        ObtenerContratosPorTipoRequest requestServicio = new ObtenerContratosPorTipoRequest();
        ObtenerContratosPorTipoResponse response = null;
        responseMensaje = new Response();

        requestServicio.setTipoContrato(Parametro.TRANSPORTE_NACIONAL);

        try {
            response = obtenerTodosContratosService.obtenerContratos(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getContratos());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - obtenerContratosTranportenacional:", e);
        }
    }

    @GetMapping("/contratos/transporte/nacional/gestion/{gestion}")
    public ResponseEntity<?> obtenerContratosTranporteNacional(Integer gestion) {
        ObtenerContratosPorTipoRequest requestServicio = new ObtenerContratosPorTipoRequest();
        ObtenerContratosPorTipoResponse response = null;
        responseMensaje = new Response();

        requestServicio.setTipoContrato(Parametro.TRANSPORTE_NACIONAL);
        requestServicio.setGestionContrato(gestion);

        try {
            response = obtenerTodosContratosService.obtenerContratosGestion(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getContratos());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - obtenerContratosTranportenacional:", e);
        }
    }

    @GetMapping("/contratos/transporte/nacional/totales/{gestion}")
    public ResponseEntity<?> obtenerTotalesContratoTranporteNacional(Integer gestion) {
        ObtenerTotalesContratoRequest requestServicio = new ObtenerTotalesContratoRequest();
        ObtenerTotalesContratoResponse response = null;
        responseMensaje = new Response();

        requestServicio.setTipo("TRANS");
        requestServicio.setAmbito("NAL");
        requestServicio.setMedio("CISTER");
        requestServicio.setGestionContrato(gestion);

        try {
            response = obtenerTotalesContratoService.obtenerTotalesPorTipoContrato(requestServicio);
            responseMensaje.setSuccess(true);
            responseMensaje.setCode(HttpStatus.OK.value());
            responseMensaje.setMessage("ok");
            responseMensaje.setData(response.getTotalesDashboardObjeto());

            return new ResponseEntity<>(
                    responseMensaje,
                    HttpStatus.OK);
        } catch (Exception e) {
            return responseMensaje.retornarExcepcion("servicio rest - obtenerContratosTranportenacional:", e);
        }
    }
}