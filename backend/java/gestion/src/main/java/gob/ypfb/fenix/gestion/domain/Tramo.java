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
@Table(name = "tramo", schema = "gestion")
public class Tramo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_lote")
    private Long idLote;

    @Column(name = "es_principal")
    private Boolean esPrincipal;

    @Column(name = "punto_recepcion")
    private String puntoRecepcion;

    @Column(name = "frontera")
    private String frontera;

    @Column(name = "punto_destino")
    private String puntoDestino;

    @Column(name = "tarifa")
    private BigDecimal tarifa;

    @Column(name = "tiempo_estimado")
    private BigDecimal tiempoEstimado;

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
