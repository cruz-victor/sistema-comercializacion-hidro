import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { NgbModalConfig, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { ContratosService } from "../contratos.service";
import { DetalleContrato } from './detalle-contrato';
import { DetalleContratosService } from './detalle-contratos.service';
import { SortableHeader, SortEvent } from './sortable.directive';

@Component({
  selector: 'app-contratos',
  templateUrl: './contratos.component.html',
  styleUrls: ['./contratos.component.css'],
  providers: [DecimalPipe, DetalleContratosService]
})
export class ContratosComponent implements OnInit {
  
  gestionActual = 2021

  // Table pagination & ordering
  countries$: Observable<DetalleContrato[]>;
  total$: Observable<number>;

  @ViewChildren(SortableHeader) headers!: QueryList<SortableHeader>;

  // Data initialization
  constructor(private contratosService: ContratosService, 
              public service: DetalleContratosService,
              config: NgbModalConfig, private modalService: NgbModal) { 
    this.countries$ = service.countries$;

    this.total$ = service.total$;

    // Modal blocking
    config.backdrop = 'static';
    config.keyboard = false;
  }

  parametricaFronteras: any;

  contratos: any;

  ngOnInit(): void {
  }

  // Table ordering
  onSort({column, direction}: SortEvent) {
    // resetting other headers
    this.headers.forEach(header => {
      if (header.sortable !== column) {
        header.direction = '';
      }
    });

    this.service.sortColumn = column;
    this.service.sortDirection = direction;
  }

  // Modal
  closeResult = '';
  idContract:number = 0;

  openit(content: any, contentNal: any, contentSumi: any, id: number, tipo: string, ambito: string, medio: string) {
    this.idContract = id;

    console.log("kkkk: "+this.idContract);
    console.log(tipo, ambito, medio)

    var currentContent: any
    if (tipo==='TRANS'){
      if (ambito==='INT') currentContent = content
      else currentContent = contentNal
    }
    else currentContent = contentSumi

    this.modalService.open(currentContent, {ariaLabelledBy: 'modal-basic-title', size: 'xl'}).result.then((result) => {
      this.closeResult = `Cerrado con: ${result}`;
    }, (reason) => {
      this.closeResult = `Bye bye ${this.getDismissReason(reason)}`;
    });
  }

  openitnew(content: any) {   
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'xl'}).result.then((result) => {
      this.closeResult = `Cerrado con: ${result}`;
    }, (reason) => {
      this.closeResult = `Bye bye ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    this.idContract = 0

    


    if (reason === ModalDismissReasons.ESC) {
      return 'presionando ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'clicking atras';
    } else {
      return `por otra razon: ${reason}`;
    }
  }

  // Para nuevo
  nuevoChanged(newObj: any){
    console.log(newObj)
  }
}



