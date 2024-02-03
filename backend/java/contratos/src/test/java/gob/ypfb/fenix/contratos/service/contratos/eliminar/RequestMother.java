package gob.ypfb.fenix.contratos.service.contratos.eliminar;

public final class RequestMother {
    static EliminarContratoRequest request;


    public static EliminarContratoRequest crear(){
        request=new EliminarContratoRequest();
        request.setId(1L);
        request.setJustificacion("Eliminado desde el test");
        request.setUsuario("vcruz");
        request.setEliminado(true);

        return request;
    }
}
