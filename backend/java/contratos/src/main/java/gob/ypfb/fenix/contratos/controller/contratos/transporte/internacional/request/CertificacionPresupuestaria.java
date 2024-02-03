/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.internacional.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificacionPresupuestaria {
    private Long id;
    private String numero;
    //private BigDecimal montoNominado;
    //private BigDecimal montoEjecutado;
    private Long idProcesoContratacion;
}
