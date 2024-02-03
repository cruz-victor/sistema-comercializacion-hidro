import { Component, OnInit } from '@angular/core';

import { Toaster } from "ngx-toast-notifications";

import { NgbDateStruct, NgbDateParserFormatter, NgbDatepickerI18n } from '@ng-bootstrap/ng-bootstrap';
import { NgbDateCustomParserFormatter } from "../ngb-date-custom";
import { NgbDatepickerCustomI18n } from "../ngb-date-i18n";

import * as XLSX from "xlsx";


@Component({
  selector: 'app-programacion',
  templateUrl: './programacion.component.html',
  styleUrls: ['./programacion.component.css'],
  providers: [
    {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter},
    { provide: NgbDatepickerI18n, useClass: NgbDatepickerCustomI18n }
   ]  
})
export class ProgramacionComponent implements OnInit {

  isUpdateable:boolean = false
  isDownloadable: boolean = false;
  notificationsCarga: string = ''
  notificationsProgramacion: string = ''

  fechaInicio!: NgbDateStruct;
  fechaFin!: NgbDateStruct;
  auxFecha!: NgbDateStruct;

  constructor(private toaster: Toaster) { }

  ngOnInit(): void {
  }

  file!: File
  arrayBuffer: any
  filelist: any

  addfile(event: any)  {    
    this.file= event.target.files[0];     
    let fileReader = new FileReader();    
    fileReader.readAsArrayBuffer(this.file);     
    fileReader.onload = (e) => {    
        this.arrayBuffer = fileReader.result;    
        var data = new Uint8Array(this.arrayBuffer);    
        var arr = new Array();    
        for(var i = 0; i != data.length; ++i) {
          arr[i] = String.fromCharCode(data[i]);    
        }
        var bstr = arr.join("");    
        var workbook = XLSX.read(bstr, {type:"binary"});    
        var first_sheet_name = workbook.SheetNames[0];    
        var worksheet = workbook.Sheets[first_sheet_name];    
        var sheetJson = XLSX.utils.sheet_to_json(worksheet,{raw:true})
        console.log(XLSX.utils.sheet_to_json(worksheet,{raw:true}));  
        var arraylist = XLSX.utils.sheet_to_json(worksheet,{raw:true});     
        this.filelist = [];    
        console.log('filelist', this.filelist)    
     
    } 
    this.isUpdateable = true
    this.notificationsCarga = 'Arhivo con información consistente.'
 
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

  // for dates

  leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }

  buildDateFechaInicio(objDate:any) {
    var aux = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  buildDateFechaFin(objDate:any) {
    var aux = (objDate)?this.leftZeroing(objDate.year,4)+"-"+this.leftZeroing(objDate.month,2)+"-"+this.leftZeroing(objDate.day,2):'';
  }

  // save logistic restriccionts

  guardar(){
    this.showToast('success', 'Información logística actualizada satisfactoriamente.')
  }
  // Volumen assignment to planta

  programar(){
    this.notificationsProgramacion = 'Asignación satisfactoria de volúmenes disponibles.'

    this.showToast('success', 'Asignación satisfactoria de volúmenes disponibles.')

    this.isDownloadable = true
  }

}
