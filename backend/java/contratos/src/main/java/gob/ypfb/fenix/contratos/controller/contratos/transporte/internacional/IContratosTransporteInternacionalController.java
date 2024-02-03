/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.internacional;

import gob.ypfb.fenix.contratos.controller.contratos.transporte.internacional.request.DatosCrearContratoTransporteInternacionalRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IContratosTransporteInternacionalController {
    @Operation(summary = "Crea un contrato.", description = "Crea un contrato del tipo Transporte Internacional.")
    ResponseEntity<?> crearContratoTransporteInternacional(@RequestBody DatosCrearContratoTransporteInternacionalRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene un contrato mendiante el ID.", description = "Obtiene un contrato del tipo Transporte Internacional mendiante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerContratoTranporteInternacionalPorId(@PathVariable Long idContrato);

    @Operation(summary = "Obtiene un contrato mendiante el CODIGO.", description = "Obtiene un contrato del tipo Transporte Internacional mendiante el CODIGO (NUMERO Y GESTION) del contrato.")
    ResponseEntity<?> obtenerContratoTranporteInternacionalPorCodigo(@RequestParam Integer numeroContraro, @RequestParam Integer gestionContrato);

    @Operation(summary = "Modificar los datos de un contrato mediante el ID.", description = "Modificar los datos de un contrato del tipo Transporte Internacional mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarContratoTranporteInternacionalPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestBody  DatosCrearContratoTransporteInternacionalRequest request,  BindingResult resultadoValidacion);

    @Operation(summary = "Elimina un contrato mediante el ID.", description = "Elimina logicamente un contrato del tipo Transporte Internacional mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarContratoTranporteInternacionalPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todos los contratos.", description = "Obtiene todos los contratos del tipo Transporte Internacional.")
    ResponseEntity<?> obtenerContratosTranporteInternacional();

    @Operation(summary = "Obtiene todos los contratos de una gestion.", description = "Obtiene los contratos tipo Transporte Internacional mediante la gestion.")
    ResponseEntity<?> obtenerContratosTranporteInternacional(@PathVariable Integer gestion);

    @Operation(summary = "Obtiene todos los contratos.", description = "Obtiene todos los contratos (Suministro Internacional, Transporte Internacional, Transporte Nacional).")
    ResponseEntity<?> obtenerContratos();

    @Operation(summary = "Obtiene todos los contratos de una gestion.", description = "Obtiene los contratos (Suministro Internacional, Transporte Internacional, Transporte Nacional) mediante la gestion.")
    ResponseEntity<?> obtenerContratos(@PathVariable Integer gestion);

    @Operation(summary = "Obtiene el resumen de los contratos vigentes de una la gestion.", description = "Obtiene el resumen de totales de los contratos vigentes por gestion del tipo Transporte Internacional.")
    ResponseEntity<?> obtenerTotalesContratoTranporteInternacional(@PathVariable Integer gestion);
}
