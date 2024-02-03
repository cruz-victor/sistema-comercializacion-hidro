/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.controller.contratos.transporte.nacional.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DatosCrearContratoTransporteNacionalRequest {

    private InformacionGeneral informacionGeneral;
    private InformacionEspecifica informacionEspecifica;
    private Vigencia vigencia;
    private List<CertificacionPresupuestaria> certificacionesPresupuestarias;
    private Empresa empresa;
    private List<Lote> lotes;
    private List<Adenda> adendas;
    private TotalVolumenesMonto totalVolumenesMonto;
    private MontoTotal monto;
    private InformacionUsuario informacionUsuario;
}
