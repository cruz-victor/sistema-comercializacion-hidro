package gob.ypfb.fenix.contratos.service.contratos.totales;

public final class RequestMother {
    static ObtenerTotalesContratoRequest request;

    public static ObtenerTotalesContratoRequest crear(){
        request=new ObtenerTotalesContratoRequest();
        request.setTipo("transporte");
        request.setAmbito("internacional");
        request.setGestionContrato(2021);

        return request;
    }
}
