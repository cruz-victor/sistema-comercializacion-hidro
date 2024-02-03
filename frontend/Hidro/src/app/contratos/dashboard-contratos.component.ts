import { Component, OnInit } from '@angular/core';

import { ContratosService } from "../contratos.service";
import { ParametricasService } from "../../app/parametricas.service";

interface Totales{
  TotalGeneral: {
    cantidadContratos?: number
    monto:{
      saldo?: number
      ejecutado?: number
      montoAdjudicado?: number
    }
    volumen: []
  }
  Occidente: {
    cantidadContratos?: number
    monto:{
      saldo?: number
      ejecutado?: number
      montoAdjudicado?: number
    }
    volumen: []
  }  
  SurOriente: {
    cantidadContratos?: number
    monto:{
      saldo?: number
      ejecutado?: number
      montoAdjudicado?: number
    }
    volumen: []
  } 
  Oriente: {
    cantidadContratos?: number
    monto:{
      saldo?: number
      ejecutado?: number
      montoAdjudicado?: number
    }
    volumen: []
  } 
}
@Component({
  selector: 'app-dashboard-contratos',
  templateUrl: './dashboard-contratos.component.html',
  styleUrls: ['./dashboard-contratos.component.css']
})
export class DashboardContratosComponent implements OnInit {

  gestionActual = 2021

  constructor(private contratosService: ContratosService,
              private parametricasService: ParametricasService, 
    ) {}

  ngOnInit(): void {
    this.init();

    this.getGestionesContratos()

    this.getTotalesContratos(this.gestionActual)
  }

  init(){
    this.totalesNacionales = {
      TotalGeneral:{
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      },
      Occidente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }  ,
      SurOriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      } ,
      Oriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }     
    }

    this.totalesInternacionales = {
      TotalGeneral:{
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      },
      Occidente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }  ,
      SurOriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      } ,
      Oriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }     
    }

    this.totalesSuministros = {
      TotalGeneral:{
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      },
      Occidente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }  ,
      SurOriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      } ,
      Oriente: {
        cantidadContratos: undefined,
        monto:{
          saldo: undefined,
          ejecutado: undefined,
          montoAdjudicado: undefined
        },
        volumen: []
      }     
    }    
  }

  getTotalesContratos(gestion: number){
    this.getTotalesContratosNacionales(gestion)
    this.getTotalesContratosInternacionales(gestion)
    this.getTotalesContratosSuministros(gestion)
  }

  totalesNacionales!: Totales;
  getTotalesContratosNacionales(gestion: number){
    this.contratosService.obtenerTotalesTransporteNacional(gestion)
    .subscribe(
      result => {
        var totals: any = result
        if (totals.success) {
          this.totalesNacionales = totals.data.TransporteNacional
        }        
      },
      error => {console.log('Retrieving problems!');}
    );  
  }

  totalesInternacionales!: Totales;
  getTotalesContratosInternacionales(gestion: number){
    this.contratosService.obtenerTotalesTransporteInternacional(gestion)
    .subscribe(
      result => {
        var totals: any = result
        if (totals.success) {
          this.totalesInternacionales = totals.data.TransporteInternacional
        }        
      },
      error => {console.log('Retrieving problems!');}
    );  
  }

  totalesSuministros!: Totales;
  getTotalesContratosSuministros(gestion: number){
    this.contratosService.obtenerTotalesSuministroInternacional(gestion)
    .subscribe(
      result => {
        var totals: any = result
        if (totals.success) {
          this.totalesSuministros = totals.data.Suministro
        }        
      },
      error => {console.log('Retrieving problems!');}
    );  
  }

  parametricaGestiones!: any
  getGestionesContratos(){
    this.parametricasService.obtenerParametrica('GESTIONES_CONTRATOS')
      .subscribe(
        result => {
          var gestiones:any = result; 
          if (gestiones.success) {
            this.parametricaGestiones = gestiones.data;
          }
      },
      error => {console.log('Retrieving problems!');}
    );    
  }

}
