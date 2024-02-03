/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.crear;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Slf4j
public class CrearCertificacionRequest {
    private Long idPartida;
    private String concepto;
    private LocalDate fechaEmision;
    private String numeroCertificacion;
    private String descripcion;
    private BigDecimal montoMaximo;
    private BigDecimal montoEjecutado;
    private BigDecimal saldo;
    private String tipoServicio;
    private Long idProcesoContratacion;
    private String idLotes;
    private Long idContrato;
    private String rutaDocumentoAdjunto;
    private LocalDate fechaRegistro;
    private Boolean eliminado;
    private String usuarioAplicacion;
    private String justificacion;
    private String ipCliente;
    private String hash;
    private Long idCertificacion;

    public String getConcepto(){
        return this.concepto.toUpperCase();
    }
}
