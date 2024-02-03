/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "volumen_ejecutado", schema = "contrato")
public class VolumenEjecutado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_contrato", nullable = false)
    private Integer idContrato;

    @Column(name = "volumen", nullable = false)
    private BigDecimal volumen;

    @Column(name = "fecha_ejecucion", nullable = false)
    private LocalDate fechaEjecucion;

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
