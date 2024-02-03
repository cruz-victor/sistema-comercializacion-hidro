package gob.ypfb.fenix.contratos.service.contratos.listar;

import gob.ypfb.fenix.contratos.service.contratos.listar.porTipo.ObtenerContratosPorTipoRequest;

public final class RequestMother {
    static ObtenerContratosPorTipoRequest request;


    public static ObtenerContratosPorTipoRequest crear(){
        request=new ObtenerContratosPorTipoRequest();
        request.setTipoContrato(1);
        request.setGestionContrato(2021);

        return request;
    }
}
