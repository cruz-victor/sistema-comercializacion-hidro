/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.certificacion.eliminar;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EliminarCertificacionRequest {
    private String concepto;
    private Long id;
    private String justificacion;
    private String usuario;
    private Boolean eliminado;

    public String getConcepto(){
        return this.concepto.toUpperCase();
    }
}
