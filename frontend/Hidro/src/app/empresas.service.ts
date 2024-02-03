import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { AppSettings } from "./config.properties";

@Injectable({
  providedIn: 'root'
})
export class EmpresasService {

  constructor(private http: HttpClient) { }

  // Cisternas

  public guardarCisternas(camiones: any){
    return this.http.post<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa/cisternas/', camiones);
  }

  obtenerCisternasEmpresa(idEmpresa: String){
    return this.http.get(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa/' + idEmpresa + "/cisterna/");  
  }

  /// para lotes

  public obtenerLotesTransporteInternacional(gestion: number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/lote/INTERNACIONAL/'+gestion);    
  }

  public obtenerTramosPorLote(lote: Number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/lote/'+lote+'/tramo');
  }

  public obtenerLotesTransporteNacional(gestion: number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/lote/NACIONAL/'+gestion);    
  }

  // ------------------------ Asociaciones

  public obtenerAsociaciones(){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/asociacion/')
  }

  public obtenerAsociacion(id: Number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/asociacion/'+id);
  }

  public guardarAsociacion(asociacion: any){
    return this.http.post<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/asociacion', asociacion);
  }

  public modificarAsociacion(asociacion: any, idAsociacion: Number){
    return this.http.put<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/asociacion/' + idAsociacion, asociacion);
  }

  // ------------------------ Empresas
  
  public guardarEmpresa(empresa: any){
    return this.http.post<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa', empresa);
  }

  public modificarEmpresa(idEmpresa: Number, empresa: any){
    return this.http.put<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa/' + idEmpresa, empresa);
  }

  public obtenerEmpresas(){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa/');
  }

  public obtenerEmpresa(id: Number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_EMPRESAS + '/v1/gestiones/empresa/'+id);
  }

}
