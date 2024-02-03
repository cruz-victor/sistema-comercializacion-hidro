/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "partida", schema = "certificacion")
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "numero_partida")
    private String numeroPartida;

    @Column(name = "monto_actual")
    private BigDecimal montoActual;

    @Column(name = "monto_aprobado")
    private BigDecimal montoAprobado;

    @Column(name = "monto_incremento")
    private BigDecimal montoIncremento;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "ruta_documento_adjunto")
    private String rutaDocumentoAdjunto;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "eliminado", nullable = false)
    private Boolean eliminado;

    @Column(name = "usuario_aplicacion", nullable = false)
    private String usuarioAplicacion;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "ip_cliente")
    private String ipCliente;

    @Column(name = "hash")
    private String hash;
}
