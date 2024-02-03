/* ================================================================
 * El archivo fue creado para Y.P.F.B. Corporación, todos los Derechos reservados
 * Desarrollador: Victor Cruz Gomez (cons.vcruz@ypfb.gob.bo)
 * Fecha: 4/5/21 08:00
 * ==============================================================
 */

package gob.ypfb.fenix.certificacion.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Utilidades{
    private static final DateTimeFormatter FORMATO=DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static ObjectMapper objectMapper;

    public  static String convertirAjson(List<Integer> request) {
        objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "[]";
        try{
            json=objectMapper.writeValueAsString(request);
        }
        catch (Exception e){
            log.error("FENIX: Ocurricio una excepcion al intentar convertir el objeto a json: "+e.getMessage());
            return "[]";
        }
        return json;
    }

    public static  List<Integer> convertirAobject(String json) {
        System.out.println("---->"+json);
        ObjectMapper objectMapper=new ObjectMapper().registerModule(new JavaTimeModule());
        List<Integer> resultado = null;
        try
        {
            resultado= Arrays.asList(objectMapper.readValue(json, Integer[].class));
        }
        catch (Exception e)
        {
            log.error("FENIX: Ocurricio una excepcion al intentar convertir el json a object: "+e.getMessage());
        }

        return resultado;
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
