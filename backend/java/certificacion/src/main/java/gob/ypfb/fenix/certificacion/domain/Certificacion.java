/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.domain;

import gob.ypfb.fenix.certificacion.shared.Utilidades;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "certificacion",schema="certificacion")
public class Certificacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_partida")
    private Long idPartida;

    @Column(name = "concepto")
    private String concepto;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @Column(name = "numero_certificacion")
    private String numeroCertificacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "monto_maximo")
    private BigDecimal montoMaximo;

    @Column(name = "monto_ejecutado")
    private BigDecimal montoEjecutado;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    @Column(name = "id_proceso_contratacion")
    private Long idProcesoContratacion;

    @Column(name = "id_lotes")
    private String idLotes;

    @Column(name = "id_contrato")
    private Long idContrato;

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

    @Column(name = "id_certificacion")
    private Long idCertificacion;

    public List<Integer> getIdLotes() {
        List<Integer> resultado=new ArrayList<>();
        resultado= Utilidades.convertirAobject(this.idLotes);

        return resultado;
    }
}
