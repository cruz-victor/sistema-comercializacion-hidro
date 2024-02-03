/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.controller.certificacion;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class DatosCrearCertificacionRequest {
    private Long idPartida;
    @Pattern(regexp = "((CTTO|ADENDA|INCR|ACT|REV))", message= "El valor del concepto no está en el rango opcional (CTTO, ADENDA, INCR, ACT, REV)")
    private String concepto;
    @NotNull
    private LocalDate fechaEmision;
    @NotEmpty(message="El Numero de Certificacionn no puede estar vacio.")
    private String numeroCertificacion;
    private String descripcion;
    @NotNull(message="El monto maximo no puede estar vacio.")
    @Min(0)
    private BigDecimal montoMaximo;
    @NotNull(message="El monto ejecutado no puede estar vacio.")
    @Min(0)
    private BigDecimal montoEjecutado;
    @NotNull(message="El saldo no puede estar vacio.")
    //@Min(0)
    private BigDecimal saldo;
    private String tipoServicio;
    private Long idProcesoContratacion;
    private List<Integer> idLotes;
    private Long idContrato;
    private String rutaDocumentoAdjunto;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
    private Long idCertificacion;
}
