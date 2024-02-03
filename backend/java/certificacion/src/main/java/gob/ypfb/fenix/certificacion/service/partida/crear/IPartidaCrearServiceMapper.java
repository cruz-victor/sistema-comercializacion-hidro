/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.service.partida.crear;

import gob.ypfb.fenix.certificacion.domain.Partida;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IPartidaCrearServiceMapper {
    IPartidaCrearServiceMapper INSTANCIA= Mappers.getMapper(IPartidaCrearServiceMapper.class);

    Partida requestToPartidaPresupuestaria(CrearPartidaRequest request);
}
