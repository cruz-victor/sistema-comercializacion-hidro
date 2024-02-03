/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.controller.empresas.empresa;

import gob.ypfb.fenix.gestion.controller.empresas.empresa.request.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface IEmpresaController {
    @Operation(summary = "Crea una empresa.", description = "Crea una empresa.")
    ResponseEntity<?> crearEmpresa(@RequestBody DatosCrearEmpresaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene una empresa mendiante el ID.", description = "Obtiene una empresa mediante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerEmpresaPorId(@PathVariable Long idEmpresa);

    @Operation(summary = "Modificar los datos de una empresa mediante el ID.", description = "Modificar los datos de una empresa mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarEmpresaPorId(@PathVariable Long idEmpresa, @RequestBody DatosCrearEmpresaRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina una empresa mediante el ID.", description = "Elimina logicamente una empresa mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarEmpresaPorId(@PathVariable Long idEmpresa, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todas las empresas", description = "Obtiene todas las empresas.")
    ResponseEntity<?> obtenerEmpresas();

    @Operation(summary = "Vincula a una empresa un conjunto de cisternas.", description = "Vincula a una empresa un conjunto de cisternas.")
    ResponseEntity<?> vincularEmrpesaCisternas(@RequestBody DatosVincularEmpresaCisternasRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Desvincula a una empresa un conjunto de cisternas.", description = "Desvincula a una empresa un conjunto de cisternas.")
    ResponseEntity<?> desvincularEmpresaCisternas(@RequestBody DatosDesvincularEmpresaCisternasRequest request, BindingResult resultadoValidacion);



    @Operation(summary = "Crea una asociacion.", description = "Crea una asociacion.")
    ResponseEntity<?> crearAsociacion(@RequestBody DatosCrearAsociacionRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene una asociacion mendiante el ID.", description = "Obtiene una asociacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> obtenerAsociacionPorId(@PathVariable Long idEmpresa);

    @Operation(summary = "Modificar los datos de una asociacion mediante el ID.", description = "Modificar los datos de una asociacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> modificarAsociacionPorId(@PathVariable Long idEmpresa, @RequestBody DatosCrearAsociacionRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Elimina una asociacion mediante el ID.", description = "Elimina logicamente una asociacion mediante el ID de registro de base de dato.")
    ResponseEntity<?> eliminarAsociacionPorId(@PathVariable Long idEmpresa, @RequestParam String justificacion, @RequestParam String usuario);

    @Operation(summary = "Obtiene todas las asociaciones", description = "Obtiene todas las empresas.")
    ResponseEntity<?> obtenerAsociaciones();

    @Operation(summary = "Vincula a una asociacion un conjunto de empresas.", description = "Vincula a una asociacion un conjunto de empresas.")
    ResponseEntity<?> vincularAsociacionEmpresas(@RequestBody DatosVincularAsociacionEmpresasRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Desvincula a una asociacion un conjunto de empresas.", description = "Desvincula a una asociacion un conjunto de empresas.")
    ResponseEntity<?> desvincularAsociacionEmpresas(@RequestBody DatosDesvincularAsociacionEmpresasRequest request, BindingResult resultadoValidacion);

    @Operation(summary = "Obtiene todas las cisternas de una empresa", description = "Obtiene todas las cisternas de una empresa.")
    ResponseEntity<?> obtenerCisternasPorEmpresa(@PathVariable Long idEmpresa);
}
