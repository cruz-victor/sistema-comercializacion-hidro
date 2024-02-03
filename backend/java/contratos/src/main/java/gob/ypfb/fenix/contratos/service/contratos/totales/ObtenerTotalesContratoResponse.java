/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.service.contratos.totales;

import gob.ypfb.fenix.contratos.shared.Utilidades;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class ObtenerTotalesContratoResponse {
    private String totalesDashboard;

    public Object getTotalesDashboardObjeto(){
        return Utilidades.convertirAobjectArray(this.totalesDashboard);
    }
}
