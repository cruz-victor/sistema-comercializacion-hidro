/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.gestion.service.lotes.lote.crear.conTramos;

import gob.ypfb.fenix.gestion.shared.Lote;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public class CrearLoteRequest {
    private String tipoLote;
    private Lote lote;

    public String getTipoLote(){
        return tipoLote.toUpperCase();
    }
}


