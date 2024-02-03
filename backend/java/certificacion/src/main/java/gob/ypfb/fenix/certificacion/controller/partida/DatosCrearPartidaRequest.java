/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.partida;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Slf4j
public class DatosCrearPartidaRequest {
    @Min(1900)
    private Integer gestion;
    @Pattern(regexp = "((MAYOR|RECON|CTTOS))", message= "El valor de la categoria no está en el rango opcional (MAYOR,RECON,CTTOS)")
    private String categoria;
    private String numeroPartida;
    @Min(0)
    private BigDecimal montoActual;
    @Min(0)
    private BigDecimal montoAprobado;
    @Min(0)
    private BigDecimal montoIncremento;
    //@Min(0)
    private BigDecimal saldo;
    private String rutaDocumentoAdjunto;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
}
