/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.lotes.lote;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ILoteController {
    @Operation(summary = "Crea un lote con tramos.", description = "Crea un lote con sus tramos. valores para tipo de Lote: INTERNACIONAL, SUMINISTRO, NACIONAL, GLP.")
    ResponseEntity<?> crearLoteConTramos(@PathVariable String tipoLote, @RequestBody DatosLoteListaTramosRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina un Lote mediante el ID.", description = "Elimina logicamente un Lote mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarLotePorId(@PathVariable Long idLote, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene lotes por tipo", description = "Obtiene lotes por tipo. Valores para tipo de Lote: INTERNACIONAL, SUMINISTRO, NACIONAL, GLP.")
    ResponseEntity<?> obtenerLotesPorTipo(@PathVariable String tipoLote, @PathVariable Integer gestion);
}
