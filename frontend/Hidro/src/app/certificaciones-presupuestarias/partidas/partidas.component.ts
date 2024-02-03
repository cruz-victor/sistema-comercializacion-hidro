import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { NgbModalConfig, NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs';

import { DetallePartidaService } from '../partidas/detalle-partida.service';
import { SortableHeader, SortEvent } from './sortable.directive';

import { DetallePartida } from '../interfaces-presupuesto';

@Component({
  selector: 'app-partidas',
  templateUrl: './partidas.component.html',
  styleUrls: ['./partidas.component.css'],
  providers: [DecimalPipe, DetallePartidaService]  
})
export class PartidasComponent implements OnInit {

  gestionActual = 2021

  // Table pagination & ordering 
  partidas$: Observable<DetallePartida[]>;
  total$: Observable<number>;

  @ViewChildren(SortableHeader) headers!: QueryList<SortableHeader>;

  // Data initialization
  constructor(public service: DetallePartidaService,
              config: NgbModalConfig, private modalService: NgbModal) { 
    this.partidas$ = service.countries$;

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
  idPartid:number = 0;

  openit(content: any, id: number) {
    this.idPartid = id;

    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'xl'}).result.then((result) => {
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
    this.idPartid = 0

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



