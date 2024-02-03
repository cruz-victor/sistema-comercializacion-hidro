import { Component, OnInit, Input} from '@angular/core';

import { Toaster } from "ngx-toast-notifications";

import { ParametricasService } from "../../parametricas.service";
import { CertificacionesService } from "../../certificaciones.service";
import { PartidaPresupuestaria, Parametro} from "../interfaces-presupuesto";

@Component({
  selector: 'app-partida',
  templateUrl: './partida.component.html',
  styleUrls: ['./partida.component.css']
})
export class PartidaComponent implements OnInit {

  @Input() idPartida!: any;

  isNew: boolean = true;

  partida!: PartidaPresupuestaria;

  justificacionCambio: string = ''

  constructor(private toaster: Toaster,
    private certificacionesService: CertificacionesService,
    private parametricasService: ParametricasService) {

    this.initPartida();

    this.getCategorias();
  }

  ngOnInit(): void {

    if (+this.idPartida > 0){
      this.isNew = false
      this.certificacionesService.obtenerPartida(+this.idPartida)
        .subscribe(
          (result: any) => {
            var datum = result; 
            if (datum.success && datum.data ) {
              this.partida = datum.data.partida;
            }
        },
        error => {
          console.log('Retrieving problems!');
        }
      );
    }    

  }

  initPartida(){
    this.partida={
      id: undefined,
      gestion: (new Date()).getFullYear(),
      categoria: '',
      numeroPartida: '',
      montoActual: undefined,
      montoAprobado: undefined,
      montoIncremento: undefined,
      saldo: undefined,

      rutaDocumentoAdjunto: '',
      fechaInicio: '',
      fechaFin: '',
      usuarioAplicacion: '',
      justificacion: '',
      ipCliente: '',
      hash: ''  
    }
  }

  submittedGestion = false;
  submittedCategoria = false;
  submittedNumeroPartida = false;

  submittedJustificacion = false
  validate(): boolean{
    var valid:boolean = true
    var antiquePermitted = 2
    
    this.submittedGestion = false
    if (this.partida.gestion==null || this.partida.gestion<=(new Date()).getFullYear()-antiquePermitted){
      this.showToast('dark', 'La gestión debe ser válida.')
      this.submittedGestion = true
      valid = false
    }
    this.submittedCategoria = false
    if (this.partida.categoria.trim()=='' || this.partida.categoria=='0'){
      this.showToast('dark', 'Una categoría debe ser seleccionada.')
      this.submittedCategoria = true
    }    
    this.submittedNumeroPartida = false;
    if (this.partida.numeroPartida.trim()==''){
      this.showToast('dark', 'El numero de partida debe contener un valor válido.')
      this.submittedNumeroPartida = true
      valid = false
    }    

    if (this.idPartida>0 && this.justificacionCambio.trim()===''){
      this.showToast('dark', 'Es necesario resgistrar la justificación del cambio.')
      this.submittedJustificacion = true
      valid = false
    }
    return valid
  }

 submit(){
    if (this.validate()){
      if (this.isNew) this.save();
      else this.update();
    }
  }

  save(){
    var result:any;
    this.certificacionesService.guardarPartida(this.partida).subscribe({
      next: (data: any[]) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la partida presupuestaria se guardó satisfactoriamente.');
          this.partida.id = result.data.id;
          this.idPartida = result.data.id;
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la partida presupuestaria. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la partida presupuestaria. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  update(){
    var result:any;
    this.certificacionesService.modificarPartida(this.idPartida, this.partida).subscribe({
      next: (data: any[]) => {
        result = data;
        if (result.success){
          this.showToast('success', 'La información de la partida presupuestaria se guardó satisfactoriamente.');
          this.isNew = false;
        }
      },
      error: (error: any) => {
        var codError: Number = error.status;
        if (codError>=500) 
          this.showToast('danger', 'NO se guardó la información de la partida presupuestaria. El servidor no pudo cumplir una solicitud aparentemente válida.');
        else if (codError>=400)
          this.showToast('warning', 'NO se guardó la información de la partida presupuestaria. La solicitud contiene una sintaxis incorrecta o no puede cumplirse.');

        this.showToast('info', 'Consulte con el administrador del sistema.');
      }
    })    
  }

  // for toaster messages
  /* typeMessage: ['success', 'danger', 'warning', 'info', 'primary', 'secondary', 'dark', 'light']; */
  showToast(typeMessage: any, messageText: any) {
    this.toaster.open({
      text: messageText,
      type: typeMessage,
      position: 'top-right'
    });
  }

   // On Change monto
  onChangeMonto(value: any){
    this.partida.saldo = value
  }


  parametricaCategorias!: Array<Parametro>
  getCategorias(){
    this.parametricasService.obtenerParametrica('PARTIDAS_PRESUPUESTARIAS')
      .subscribe(
        result => {
          var categorias:any = result; 
          if (categorias.success && categorias.data) {
            this.parametricaCategorias = categorias.data.map((element: any)=> ({id:element.codigo, descripcion:element.valor}));
          }
      },
      error => {console.log('Retrieving problems!');}
    );      
  }

}
