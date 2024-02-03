/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.contratos.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Slf4j
public class Utilidades<T> {
    private static final DateTimeFormatter FORMATO=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static ObjectMapper objectMapper;

    public  String convertirContratoAjson(T request) {
        objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "[]";
        try{
            json=objectMapper.writeValueAsString(request);
        }
        catch (Exception e){
            log.error("FENIX: Ocurricio una excepcion al intentar convertir el contrato de transporte internacional a json: "+e.getMessage());
            return "[]";
        }
        System.out.println("request---->"+request);
        System.out.println("json---->"+json);
        return json;
    }

    public static  Object convertirAobjectArray(String json) {
        ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        Object resultado = null;
        try
        {
            resultado= objectMapper.readValue(json, Object.class);
        }
        catch (Exception e)
        {
            log.error("FENIX: Ocurricio una excepcion al intentar convertir el json a object: "+e.getMessage());
        }

        return resultado;
    }
}
