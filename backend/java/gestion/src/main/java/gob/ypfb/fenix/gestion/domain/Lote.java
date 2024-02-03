/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "lote", schema = "gestion")
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tipo_lote")
    private String tipoLote;

    @Column(name = "lote")
    private Integer lote;

    @Column(name = "tipo_producto")
    private String tipoProducto;

    @Column(name = "sector")
    private String sector;

    @Column(name = "volumen_maximo")
    private BigDecimal volumenMaximo;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "gestion")
    private Integer gestion;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "eliminado")
    private Boolean eliminado;

    @Column(name = "usuario_aplicacion")
    private String usuarioAplicacion;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "ip_cliente")
    private String ipCliente;

    @Column(name = "hash")
    private String hash;

}
