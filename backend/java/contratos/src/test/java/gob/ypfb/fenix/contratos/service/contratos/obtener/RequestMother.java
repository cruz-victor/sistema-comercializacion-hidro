package gob.ypfb.fenix.contratos.service.contratos.obtener;

public final class RequestMother {
    static ObtenerContratoRequest request;


    public static ObtenerContratoRequest crear(){
        request=new ObtenerContratoRequest();
        request.setId(27L);
        request.setTipoContrato(1);
        request.setNumeroContrato(123);
        request.setGestionContrato(2020);

        return request;
    }
}
