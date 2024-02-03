/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.suministro;

import gob.ypfb.fenix.contratos.controller.contratos.suministro.request.DatosCrearContratoSuministroImportacionRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IContratosSuministroImportacionController {
    @Operation(summary = "Crea un contrato.", description = "Crea un contrato del tipo Suministro Importacion.")
    ResponseEntity<?> crearContratoSuministroImportacion(@RequestBody DatosCrearContratoSuministroImportacionRequest request,BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene un contrato mendiante el ID.", description = "Obtiene un contrato del tipo Suministro Importacion mendiante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerContratoSuministroImportacionPorId(@PathVariable Long idContrato);

    @Operation(summary = "Obtiene un contrato mendiante el CODIGO.", description = "Obtiene un contrato del tipo Suministro Importacion mendiante el CODIGO (NUMERO Y GESTION) del contrato.")
    ResponseEntity<?> obtenerContratoSuministroImportacionPorCodigo(@RequestParam Integer numeroContraro, @RequestParam Integer gestionContrato);

    @Operation(summary = "Modificar los datos de un contrato mediante el ID.", description = "Modificar los datos de un contrato del tipo Suministro Importacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarContratoSuministroImportacionPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestBody DatosCrearContratoSuministroImportacionRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina un contrato mediante el ID.", description = "Elimina logicamente un contrato del tipo Suministro Importacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarContratoSuministroImportacionPorId(@PathVariable Long idContrato, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todos los contratos.", description = "Obtiene todos los contratos del tipo Suministro Importacion.")
    ResponseEntity<?> obtenerContratosSuministroImportacion();

    @Operation(summary = "Obtiene todos los contratos de una gestion.", description = "Obtiene los contratos tipo Suministro Importacion mediante la gestion.")
    ResponseEntity<?> obtenerContratosSuministroImportacion(@PathVariable Integer gestion);

    @Operation(summary = "Obtiene el resumen de los contratos vigentes de una la gestion.", description = "Obtiene el resumen de totales de los contratos vigentes por gestion del tipo Suministro Importacion.")
    ResponseEntity<?> obtenerTotalesContratoSuministroImportacion(@PathVariable Integer gestion);
}

