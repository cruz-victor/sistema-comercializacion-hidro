/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.totales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObtenerTotalesContratoRequest {
    private String tipo;
    private String ambito;
    private String medio;
    private Integer gestionContrato;

    public String getTipo() {
        return tipo.toUpperCase();
    }

    public String getAmbito() {
        return ambito.toUpperCase();
    }

    public String getMedio() {
        return medio.toUpperCase();
    }
}
