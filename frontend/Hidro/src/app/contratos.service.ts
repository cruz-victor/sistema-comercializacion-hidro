import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { AppSettings } from "./config.properties";

@Injectable({
  providedIn: 'root'
})
export class ContratosService {

  constructor(private http: HttpClient) { }

  //-------------------------------------------------- Contratos Internacionales

  public guardarContrato(contrato: any): any{
    return this.http.post<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/internacional', contrato);
  }

  public modificarContrato(idContrato: number, contrato: any, justificacion: string): any{    
    return this.http.put<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/internacional/' + idContrato + '?justificacion=' + justificacion, contrato);
  }
 
  public obtenerContrato(numero: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/internacional/' + numero);   
  }

  public obtenerContratos(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/internacional/gestion/' + gestion);
  }

  //-------------------------------------------------- Contratos Nacionales

  public guardarContratoNacional(contrato: any): any{
    return this.http.post<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/nacional', contrato);
  }

  public modificarContratoNacional(idContrato: number, contrato: any, justificacion: string): any{    
    return this.http.put<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/nacional/' + idContrato + '?justificacion=' + justificacion, contrato);
  }
 
  public obtenerContratoNacional(numero: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/nacional/' + numero);   
  }

  public obtenerContratosNacionales(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/nacional/gestion/' + gestion);
  }

  //-------------------------------------------------- Contratos Suministro 

  public guardarContratoSuministro(contrato: any): any{
    return this.http.post<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/suministro/importacion/', contrato);
  }

  public modificarContratoSuministro(idContrato: number, contrato: any, justificacion: string): any{    
    return this.http.put<any>(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/suministro/importacion/' + idContrato + '?justificacion=' + justificacion, contrato);
  }
  
  public obtenerContratoSuministro(numero: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/suministro/importacion/' + numero);   
  }
  
  public obtenerContratosSuministros(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/suministro/importacion/gestion/' + gestion);
  }

  //-------------------------------------------------- Todos los contratos}

  public obtenerTodosContratos(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/gestion/' + gestion);
  }

  //-------------------------------------------------- Totales

  public obtenerTotalesTransporteInternacional(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/internacional/totales/' + gestion);
  }

  public obtenerTotalesTransporteNacional(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/transporte/nacional/totales/' + gestion);    
  }

  public obtenerTotalesSuministroInternacional(gestion: number){
    return this.http.get(AppSettings.API_ENDPOINT_CONTRATOS + '/v1/contratos/suministro/importacion/totales/' + gestion);
  }
  
}
