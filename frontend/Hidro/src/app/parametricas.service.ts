import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "./config.properties";

@Injectable({
  providedIn: 'root'
})
export class ParametricasService {

  constructor(private http: HttpClient) { }

  obtenerParametrica(param: String){
    return this.http.get(AppSettings.API_ENDPOINT_PARAMETRICAS + "/v1/parametro/" + param);
  }

}
