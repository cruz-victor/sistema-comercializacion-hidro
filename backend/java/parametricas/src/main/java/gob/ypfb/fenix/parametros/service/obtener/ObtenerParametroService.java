/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.parametros.service.obtener;

import gob.ypfb.fenix.parametros.domain.Parametro;
import gob.ypfb.fenix.parametros.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ObtenerParametroService implements IObtenerParametroService{

    @Autowired
    private ParametroRepository repository;

    @Override
    @Transactional(readOnly = true)
    public ObtenerParametroResponse obtenerParametroPorTipo(ObtenerParametroRequest request) {
        List<Parametro> parametros= repository.findParametrosPorTipo(request.getTipo());
        ObtenerParametroResponse response=new ObtenerParametroResponse();
        response.setParametros(parametros);

        return response;
    }
}

