/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.procesoContratacion;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface IProcesoContratacionController {

    @Operation(summary = "Obtiene todo los procesos de contratacion", description = "Obtiene todo los procesos de contratacion.")
    ResponseEntity<?> obtenerProcesosPorGestion();

//    @Operation(summary = "Obtiene todas las Certificaciones de un proceso de contratacion", description = "Obtiene todas las Certificaciones de un proceso de contratacion.")
//    ResponseEntity<?> obtenerCertificacionesPorProcesoContratacion( @PathVariable Long idProcesoContratacion);
}
