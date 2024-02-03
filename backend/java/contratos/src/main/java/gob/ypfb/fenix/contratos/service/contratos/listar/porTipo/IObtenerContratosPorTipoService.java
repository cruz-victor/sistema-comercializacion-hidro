/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.listar.porTipo;

public interface IObtenerContratosPorTipoService {

    ObtenerContratosPorTipoResponse obtenerContratos(ObtenerContratosPorTipoRequest request);
    ObtenerContratosPorTipoResponse obtenerContratosGestion(ObtenerContratosPorTipoRequest request);
}