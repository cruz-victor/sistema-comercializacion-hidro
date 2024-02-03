/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.shared;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Lote {
    @Min(0)
    private Integer lote;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @Min(1900)
    private Integer gestion;
    private String usuarioAplicacion;
    private String ipCliente;
    private String hash;

    private Tramos tramos;
}
