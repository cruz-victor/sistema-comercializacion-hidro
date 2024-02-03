/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.lotes.tramo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ITramoController {
    @Operation(summary = "Obtiene los tramos por el ID lote", description = "Obtiene los tramos por el ID de registro del lote")
    ResponseEntity<?> obtenerTramosPorLote(@PathVariable Long idLote);
}
