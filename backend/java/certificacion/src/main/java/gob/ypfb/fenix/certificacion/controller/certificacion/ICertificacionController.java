/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.certificacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ICertificacionController {
    @Operation(summary = "Crea una Certificacion.", description = "Crea una Certificacion.")
    ResponseEntity<?> crearCertificacion(@RequestBody DatosCrearCertificacionRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene una Certificacion mendiante el ID.", description = "Obtiene una Certificacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerCertificacionPorId(@PathVariable Long idCertificacion);

    @Operation(summary = "Modificar los datos de una Certificacion mediante el ID.", description = "Modificar los datos de una Certificacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarCertificacionPorId(@PathVariable Long idCertificacion, @RequestBody DatosCrearCertificacionRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina una Certificacion mediante el ID.", description = "Elimina logicamente una Certificacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarCertificacionPorId(@PathVariable Long idCertificacion, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene certificaciones por concepto", description = "Obtiene certificaciones por concepto.")
    ResponseEntity<?> obtenerCertificacionesPorTipoCertificacion(@PathVariable String concepto);

    @Operation(summary = "Obtiene todas las certificaciones", description = "Obtiene todas las certificaciones.")
    ResponseEntity<?> obtenerCertificaciones();

    @Operation(summary = "Obtiene el resumen de todas certificaciones", description = "Obtiene el resumen de todas certificaciones.")
    ResponseEntity<?> obtenerTotalesCertificaciones(@PathVariable Integer gestion) throws JsonProcessingException;
}
