import { Component, OnInit, Input } from '@angular/core';
import {NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import { ParametricasService } from "../../parametricas.service";
import { ChangeDetectorRef } from "@angular/core";

import { Adenda, ParametrosLote, Lote, Parametro, CertificacionPresupuestaria } from "../interfaces-contratos";
import { CertificacionesService } from "../../certificaciones.service";

@Component({
  selector: 'app-modificatorio',
  templateUrl: './modificatorio.component.html',
  styleUrls: ['./modificatorio.component.css']
})
export class ModificatorioComponent implements OnInit {

  @Input() parametrosLote!: Array<ParametrosLote>
  @Input() tramosLotecillosAdenda!: Array<Lote>

  @Input() adendas: Array<Adenda> = []
  @Input() ambito!: string

  constructor(private parametricasService: ParametricasService, 
    private certificacionesService: CertificacionesService,
    private cdRef:ChangeDetectorRef) { }

  ngOnInit(): void {
    this.getAllCertificacionesForAdenda()
    this.getProductos()
  }

  // Date treatment

  // fechaSuscripcion!: NgbDateStruct;
  fechaSuscripcion: Array<NgbDateStruct> = [];
  fechaFinalizacion: Array<NgbDateStruct> = []

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }
  
  buildDateSuscripcion(objDate:any, index: any) {
    this.adendas[index].fecha = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  buildDateFinalizacion(objDate:any, index: any) {
    this.adendas[index].vigencia.fechaFinalizacion = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  //Tramos
 
  addRowTramoPrincipal(i: any, tl: any) {   
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:"", tarifa: undefined};  
    this.adendas[i].lotes[tl].tramos.principales.push(nuevoTramo)
    return true;  
  }  

  deleteRowTramoPrincipal(i: any, tl: any, it: any) {  
    this.adendas[i].lotes[tl].tramos.principales.splice(it, 1)
  }  

  addRowTramoSecundario(i: any, tl: any) {   
    var nuevoTramo = {puntoRecepcion: "", frontera: "",puntoEntrega:"", tarifa: undefined};  
    this.adendas[i].lotes[tl].tramos.secundarios.push(nuevoTramo)
    return true;  
  }  

  deleteRowTramoSecundario(i: any, tl: any, it: any) {   
    this.adendas[i].lotes[tl].tramos.secundarios.splice(it, 1)
  }   

  // Adendas

  addRowAdendas(){
    var nuevaAdenda: Adenda = {fecha:'', objeto:'', 
                              certificacionesPresupuestarias: [], //{id: 0, idProcesoContratacion: undefined, numero: undefined}, 
                              vigencia:{fechaFinalizacion:''}, 
                              monto: {montoAdjudicado: undefined},
                              productos: [], //nominacionVolumen: {porcentajeNominado: undefined, volumenNominado: undefined},
                              lotes: [] } 

    //let tramos = Object.assign([], this.tramosLotecillosAdenda);
    //const another = {...newObect} 
    //let tramos = [...this.tramosLotecillosAdenda]
    this.tramosLotecillosAdenda.forEach((element:any)=>{
      var tramis = Object.assign({}, { lote:element.lote, tramos: {principales: [], secundarios: []} });
      nuevaAdenda.lotes.push(tramis);
    })

    this.adendas.push(nuevaAdenda);

    this.fechaSuscripcion.push();
    this.fechaFinalizacion.push()
    
    return true;
  }  

  deleteRowAdendas(index: any) {  
    this.adendas.splice(index, 1);  
  }  

  // Certificaciones Presupuestarias

  parametricaAllCertificacionesForAdenda!: Array<Parametro>
  getAllCertificacionesForAdenda(){
    this.certificacionesService.obtenerTodasCertificaciones()
    .subscribe(
      result => {
        if (result.success) {
          var todasCertificaciones = result.data.certificaciones
          
          this.parametricaAllCertificacionesForAdenda = todasCertificaciones
            .filter((e:any)=>(e.concepto==='ADENDA'||e.concepto==='CTTO'))
            .map((a:any)=>({id: a.id, descripcion: 'No. '+a.numeroCertificacion+' ('+a.concepto+') - '+a.fechaEmision}))

            // console.log('certificaciones.....',this.parametricaAllCertificacionesForAdenda)
          // var adendas = outcome.data.certificaciones
          // adendas.forEach((ele: any) => {
          //   this.parametricaCertificaciones.push({id: ele.numeroCertificacion, descripcion: ele.numeroCertificacion+' ('+ele.concepto+')'})
          // });
        }        
      },
      error => {console.log('Retrieving problems!');}
    );      
  } 

  addRowCertificacionAdenda(indexAdenda: any) {     
    var nuevaCertificacion = {id: 0, idProcesoContratacion: undefined, numero: undefined};  
    this.adendas[indexAdenda].certificacionesPresupuestarias.push(nuevaCertificacion)
    return true;  
  } 

  deleteRowCertificacionAdenda(indexAdenda: any, indexItem: any) {  
    this.adendas[indexAdenda].certificacionesPresupuestarias.splice(indexItem, 1);
    return true;
  }

  // Productos

  parametricaProductosAdenda: any;
  getProductos(){
    this.parametricasService.obtenerParametrica('PRODUCTOS_INTERNACIONALES')
      .subscribe(
        result => {
          var datum: any = result;
          if (datum.success) {
            this.parametricaProductosAdenda = datum.data.map((element:any)=>({id: element.codigo, descripcion: element.valor}))
            console.log(this.parametricaProductosAdenda)
          }
      },
      error => {console.log('Retrieving problems!');}
    ); 
  }

  addRowProductosAdenda(indexAdenda: any) {     
    var nuevoProducto = {producto: '', porcentajeAdjudicado: undefined, volumenAdjudicado: undefined, mermaPermitida: undefined};  
    this.adendas[indexAdenda].productos.push(nuevoProducto)
    return true;  
  } 

  deleteRowProductosAdenda(indexAdenda: any, indexProducto: any) {  
    this.adendas[indexAdenda].productos.splice(indexProducto, 1);
    return true;
  } 

}
