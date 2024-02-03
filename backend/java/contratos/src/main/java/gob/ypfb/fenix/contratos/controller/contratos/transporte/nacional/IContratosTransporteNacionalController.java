/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.nacional;

import gob.ypfb.fenix.contratos.controller.contratos.transporte.nacional.request.DatosCrearContratoTransporteNacionalRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IContratosTransporteNacionalController {
    @Operation(summary = "Crea un contrato.", description = "Crea un contrato del tipo Transporte Nacional.")
    ResponseEntity<?> crearContratoTransporteNacional(@RequestBody DatosCrearContratoTransporteNacionalRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene un contrato mendiante el ID.", description = "Obtiene un contrato del tipo Transporte Nacional mendiante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerContratoTranporteNacionalPorId(@PathVariable Long idContrato);

    @Operation(summary = "Obtiene un contrato mendiante el CODIGO.", description = "Obtiene un contrato del tipo Transporte Nacional mendiante el CODIGO (NUMERO Y GESTION) del contrato.")
    ResponseEntity<?> obtenerContratoTranporteNacionalPorCodigo(@RequestParam Integer numeroContraro, @RequestParam Integer gestionContrato);

    @Operation(summary = "Modificar los datos de un contrato mediante el ID.", description = "Modificar los datos de un contrato del tipo Transporte Nacional mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarContratoTranporteNacionalPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestBody DatosCrearContratoTransporteNacionalRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina un contrato mediante el ID.", description = "Elimina logicamente un contrato del tipo Transporte Nacional mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarContratoTranporteNacionalPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todos los contratos.", description = "Obtiene todos los contratos del tipo Transporte Nacional.")
    ResponseEntity<?> obtenerContratosTranporteNacional();

    @Operation(summary = "Obtiene todos los contratos de una gestion.", description = "Obtiene los contratos tipo Transporte Nacional mediante la gestion.")
    ResponseEntity<?> obtenerContratosTranporteNacional(@PathVariable Integer gestion);

    @Operation(summary = "Obtiene el resumen de los contratos vigentes de una la gestion.", description = "Obtiene el resumen de totales de los contratos vigentes por gestion del tipo Transporte Nacional.")
    ResponseEntity<?> obtenerTotalesContratoTranporteNacional(@PathVariable Integer gestion);
}
