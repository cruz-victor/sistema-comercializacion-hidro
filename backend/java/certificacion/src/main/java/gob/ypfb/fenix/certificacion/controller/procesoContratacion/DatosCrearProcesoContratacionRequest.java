/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.procesoContratacion;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Slf4j
public class DatosCrearProcesoContratacionRequest {
    private Long gestion;
    private String categoria;
    private String numeroPartida;
    private BigDecimal montoActual;
    private BigDecimal montoAprobado;
    private BigDecimal montoIncremento;
    private BigDecimal saldo;
    private String rutaDocumentoAdjunto;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
}
