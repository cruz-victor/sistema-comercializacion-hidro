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
@Table(name = "cisterna", schema="gestion")
public class Cisterna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "placa", nullable = false)
    private String placa;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "marca")
    private String marca;

    @Column(name = "color")
    private String color;

    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "nro_compartimientos")
    private Integer nroCompartimientos;

    @Column(name = "nro_chasis")
    private String nroChasis;

    @Column(name = "disposicion_ejes")
    private String disposicionEjes;

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
