/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.partida;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPartidaController {
    @Operation(summary = "Crea una Partida.", description = "Crea una Partida.")
    ResponseEntity<?> crearPartida(@RequestBody DatosCrearPartidaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene una Partida mendiante el ID.", description = "Obtiene una Partida mediante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerPartidaPorId(@PathVariable Long idPartida);

    @Operation(summary = "Modificar los datos de una Partida.", description = "Modificar los datos de una Partida mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarPartidaPorId(@PathVariable Long idPartida, @RequestBody DatosCrearPartidaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina una Partida mediante el ID.", description = "Elimina logicamente una Partida mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarPartidaPorId(@PathVariable Long idPartida, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todas las Partidas", description = "Obtiene todas las Partidas.")
    ResponseEntity<?> obtenerPartidas();

    @Operation(summary = "Obtiene el resumen de todas partidas", description = "Obtiene el resumen de todas las partidas.")
    ResponseEntity<?> obtenerTotalesPartidas(@PathVariable Integer gestion) throws JsonProcessingException;
}
