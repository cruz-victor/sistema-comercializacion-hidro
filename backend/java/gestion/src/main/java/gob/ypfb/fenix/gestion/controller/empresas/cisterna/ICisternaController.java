/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.cisterna;

import gob.ypfb.fenix.gestion.controller.empresas.cisterna.request.DatosCrearCisternaRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICisternaController {
    @Operation(summary = "Crea una cisterna.", description = "Crea una cisterna.")
    ResponseEntity<?> crearCisterna(@RequestBody DatosCrearCisternaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene una cisterna mendiante el ID.", description = "Obtiene una cisterna mediante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerCisternaPorId(@PathVariable Long idCisterna);

    @Operation(summary = "Modificar los datos de una cisterna.", description = "Modificar los datos de una cisterna mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarCisternaPorId(@PathVariable Long idCisterna, @RequestBody DatosCrearCisternaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina una cisterna mediante el ID.", description = "Elimina logicamente una cisterna mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarCisternaPorId(@PathVariable Long idCisterna, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todas las cisternas", description = "Obtiene todas las cisterna.")
    ResponseEntity<?> obtenerCisternas();
}
