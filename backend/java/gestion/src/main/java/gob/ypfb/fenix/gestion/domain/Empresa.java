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
import java.time.LocalDate;

@Data
@Entity
@Table(name = "empresa", schema="gestion")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "pais")
    private String pais;

    @Column(name = "es_asociacion")
    private Boolean esAsociacion;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "tipo_constitucion")
    private String tipoConstitucion;

    @Column(name = "email")
    private String email;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "sitio_web")
    private String sitioWeb;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "representante_legal")
    private String representanteLegal;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

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
