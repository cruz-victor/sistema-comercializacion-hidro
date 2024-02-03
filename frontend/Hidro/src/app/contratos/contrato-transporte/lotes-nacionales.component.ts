import { Component, OnInit } from '@angular/core';

import { Toaster } from "ngx-toast-notifications";

import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../../ngb-date-i18n";

import { EmpresasService } from "../../../app/empresas.service";

interface Tramo {
  puntoRecepcion: string;  
  frontera: string;  
  puntoEntrega: string; 
  tarifa?: Number;
}
 
interface Lote {
  lote?: Number,
  fechaInicio: String,
  fechaFin: String,
  gestion?: Number
  tramos:{
    principales: Array<Tramo>,
    secundarios: Array<Tramo>
  },
  usuarioAplicacion: String,
  ipCliente: String,
  hash: String
}
interface ObjetoLote{
  lote: Lote;
}

interface Parametro {
  codigo: string;  
  valor: string;  
}

@Component({
  selector: 'app-lotes-nacionales',
  templateUrl: './lotes-nacionales.component.html',
  styleUrls: ['./lotes-nacionales.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]   
})
export class LotesNacionalesComponent implements OnInit {

  lotecillo!: Lote;
  objLote!: ObjetoLote;

  isNew: boolean = true;
  postId: any;

  gestionActual!: number

  constructor(private empresasService: EmpresasService,
              private toaster: Toaster ) { 
    this.gestionActual = (new Date()).getFullYear()
  }

  ngOnInit(): void {
    this.lotecillo = {lote: undefined, fechaInicio:'', fechaFin:'', gestion: (new Date()).getFullYear(), 
                      tramos: {principales: [], secundarios: []}, usuarioAplicacion: 'Txema', ipCliente: '', hash: ''};
    this.objLote = {lote: this.lotecillo};

    this.getLotesTransporteInternacional();
  }

  submit(){
    if (this.isNew) this.save();
    else this.update();
  }

  save(){

  }

  update(){

  }

  // Obtener lotes transporte internacional
  lotesTransInter: any;
  getLotesTransporteInternacional(){
    this.empresasService.obtenerLotesTransporteNacional(this.gestionActual)
    .subscribe(
      result => {
        this.lotesTransInter = result; 
        if (this.lotesTransInter.success) {
          this.lotesTransInter = this.lotesTransInter.data.lotes;
        }
    },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }

  tramosLote: any;
  puntoRecepciones!: Array<Parametro>; 
  fronteras!: Array<Parametro>; 
  puntoEntregas!: Array<Parametro>; 

  loteChanged(newObj: any) {
    if (newObj>0){
      var currentLote = this.lotesTransInter.filter((l:any)=>l.id==newObj)
      console.log(currentLote)
      this.lotecillo.fechaInicio = currentLote[0].fechaInicio
      this.lotecillo.fechaFin= currentLote[0].fechaFin
      if (this.lotecillo.fechaInicio){
        var currentDateIni: Array<string> = this.lotecillo.fechaInicio.split('-')
        this.fechaInicio = {year: +currentDateIni[0], month: +currentDateIni[1], day:+currentDateIni[2]}
      }
      if (this.lotecillo.fechaFin){
        var currentDateFin: Array<string> = this.lotecillo.fechaFin.split('-')
        this.fechaInicio = {year: +currentDateFin[0], month: +currentDateFin[1], day:+currentDateFin[2]}
      }
    }
    else{
      this.fechaInicio = {...this.auxFecha}
      this.fechaFin = {...this.auxFecha}
    } 

    this.empresasService.obtenerTramosPorLote(newObj)
    .subscribe(
      result => {
        this.tramosLote = result; 
        if (this.tramosLote.success) {
          this.tramosLote = this.tramosLote.data.tramos;

          var origenes = this.tramosLote
          .reduce((acc: any, cur: any)=>{
            if (acc[cur.puntoRecepcion]) acc[cur.puntoRecepcion]++; else acc[cur.puntoRecepcion]=1
            return acc
          },{});          
          this.puntoRecepciones = []; 
          for (var key in origenes) this.puntoRecepciones.push({codigo: key, valor: key});
          // console.log(this.puntoRecepciones);

          var fronteras = this.tramosLote
          .reduce((acc: any, cur: any)=>{
            if (acc[cur.frontera]) acc[cur.frontera]++; else acc[cur.frontera]=1
            return acc
          },{});          
          this.fronteras = []; 
          for (var key in fronteras) this.fronteras.push({codigo: key, valor: key});
          // console.log(this.fronteras);

          var destino = this.tramosLote
          .reduce((acc: any, cur: any)=>{
            if (acc[cur.puntoDestino]) acc[cur.puntoDestino]++; else acc[cur.puntoDestino]=1
            return acc
          },{});          
          this.puntoEntregas = []; 
          for (var key in destino) this.puntoEntregas.push({codigo: key, valor: key});
          // console.log(this.puntoEntregas);

          //this.tramosPrincipales = this.tramosLote.filter((element: any) => element.esPrincipal);
          this.lotecillo.tramos.principales = this.tramosLote
            .filter((element: any) => element.esPrincipal)
            .map((e:any)=>{return {puntoRecepcion: e.puntoRecepcion, frontera: e.frontera, puntoEntrega: e.puntoDestino, tarifa: e.tarifa}});

          //this.tramosSecundarios = this.tramosLote.filter((element: any) => !element.esPrincipal);
          this.lotecillo.tramos.secundarios = this.tramosLote
            .filter((element: any) => !element.esPrincipal)
            .map((e:any)=>{return {puntoRecepcion: e.puntoRecepcion, frontera: e.frontera, puntoEntrega: e.puntoDestino, tarifa: e.tarifa}});
        }
    },
    error => {
      console.log('Retrieving problems!');
    }
    );    
  }
  
  // Tramos
  addRowTramoPrincipal() {     
    var nuevoTramo = {puntoRecepcion: "", frontera: "", puntoEntrega:""};  
    this.lotecillo.tramos.principales.push(nuevoTramo)
    return true;  
  }  

  deleteRowTramoPrincipal(indexTramo: any) {  
    if(this.lotecillo.tramos.principales.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 
      var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
      this.lotecillo.tramos.principales.pop();
      this.lotecillo.tramos.principales.push(nuevoTramo);
      return false;  
    } else {  
      this.lotecillo.tramos.principales.splice(indexTramo, 1);  
      return true;  
    }  
  } 

  addRowTramoSecundario() {     
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};  
    this.lotecillo.tramos.secundarios.push(nuevoTramo)
    return true;  
  } 

  deleteRowTramoSecundario(indexTramo: any) {  
    if(this.lotecillo.tramos.secundarios.length == 1) {  
      console.log("Can't delete the row when there is only one row"); 
      var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:""};
      this.lotecillo.tramos.secundarios.pop();
      this.lotecillo.tramos.secundarios.push(nuevoTramo);
      return false;  
    } else {  
      this.lotecillo.tramos.secundarios.splice(indexTramo, 1);  
      return true;  
    }  
  } 
  
  //Fechas
  fechaInicio!: NgbDateStruct;
  fechaFin!: NgbDateStruct;
  auxFecha!: NgbDateStruct;

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }

  buildDateFechaInicio(objDate:any) {
    this.lotecillo.fechaInicio = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  buildDateFechaFin(objDate:any) {
    this.lotecillo.fechaFin = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

    // Toaster
  /* typeMessage: ['success', 'danger', 'warning', 'info', 'primary', 'secondary', 'dark', 'light']; */
  showToast(typeMessage: any, messageText: any) {
    this.toaster.open({
      text: messageText,
      type: typeMessage,
      position: 'top-right'
    });
  }
  
}

