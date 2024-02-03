import { Injectable } from '@angular/core';
import { HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CertificacionesService {

  constructor(private http: HttpClient) { }

  public guardarPartida(partida: any): any{
    return this.http.post<any>('http://lpz-p-213412:9400/api-fenix/v1/PartidaPresupuestaria/partida', partida);
  }

  public modificarPartida(idPartida: number, partida: any): any{    
    return this.http.put<any>('http://lpz-p-213412:9400/api-fenix/v1/PartidaPresupuestaria/partida/' + idPartida, partida);
  }

  public obtenerPartidas(){
    return this.http.get<any>('http://lpz-p-213412:9400/api-fenix/v1/PartidaPresupuestaria/partida/');
  }

  public obtenerPartida(idPartida: number){
    return this.http.get<any>('http://lpz-p-213412:9400/api-fenix/v1/PartidaPresupuestaria/partida/'+idPartida);
  }

  public guardarCertificacion(certificacion: any): any{
    return this.http.post<any>('http://lpz-p-213412:9400/api-fenix/v1/certificacionPresupuestaria/partida/certificacion/', certificacion);
  }

  public modificarCertificacion(idCertificacion: number, certificacion: any): any{    
    return this.http.put<any>('http://lpz-p-213412:9400/api-fenix/v1/certificacionPresupuestaria/partida/certificacion/' + idCertificacion, certificacion);
  }

  public obtenerCertificaciones(concepto: String){
    return this.http.get<any>('http://lpz-p-213412:9400/api-fenix/v1/certificacionPresupuestaria/partida/certificacion/concepto/' + concepto);
  }

  public obtenerCertificacion(idCertificacion: number){
    return this.http.get<any>('http://lpz-p-213412:9400/api-fenix/v1/certificacionPresupuestaria/partida/certificacion/' + idCertificacion);
  }

  public obtenerTodasCertificaciones(){
    return this.http.get<any>('http://10.11.10.56:9400/api-fenix/v1/certificacionPresupuestaria/partida/certificacion/');
  }

  public obtenerProcesosContratacion(){
    return this.http.get<any>('http://lpz-p-213412:9400/api-fenix/v1/procesoContratacion/')
  }

}
