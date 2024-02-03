import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";
import { AppSettings } from "./config.properties";

@Injectable({
  providedIn: 'root'
})
export class CertificacionesService {

  constructor(private http: HttpClient) { }

  // ----------- Partidas

  public guardarPartida(partida: any): any{
    return this.http.post<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/PartidaPresupuestaria/partida', partida);
  }

  public modificarPartida(idPartida: number, partida: any): any{    
    return this.http.put<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/PartidaPresupuestaria/partida/' + idPartida, partida);
  }

  public obtenerPartidas(){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/PartidaPresupuestaria/partida/');
  }

  public obtenerPartida(idPartida: number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/PartidaPresupuestaria/partida/'+idPartida);
  }

  // -------------- Certificación

  public guardarCertificacion(certificacion: any): any{
    return this.http.post<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/certificacionPresupuestaria/partida/certificacion/', certificacion);
  }

  public modificarCertificacion(idCertificacion: number, certificacion: any): any{    
    return this.http.put<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/certificacionPresupuestaria/partida/certificacion/' + idCertificacion, certificacion);
  }

  public obtenerCertificaciones(concepto: String){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/certificacionPresupuestaria/partida/certificacion/concepto/' + concepto);
  }

  public obtenerCertificacion(idCertificacion: number){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/certificacionPresupuestaria/partida/certificacion/' + idCertificacion);
  }

  public obtenerTodasCertificaciones(){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/certificacionPresupuestaria/partida/certificacion/');
  }

  public obtenerProcesosContratacion(){
    return this.http.get<any>(AppSettings.API_ENDPOINT_PRESUPUESTO + '/v1/procesoContratacion/')
  }

}
