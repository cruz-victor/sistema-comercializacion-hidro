import {Injectable, PipeTransform} from '@angular/core';
import {DecimalPipe} from '@angular/common';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';

import {SortColumn, SortDirection} from './sortable.directive';
import { ContratosService } from "../contratos.service";
import { DetalleContrato } from './detalle-contrato';

interface SearchResult {
  countries: DetalleContrato[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  sortColumn: SortColumn;
  sortDirection: SortDirection;
}

const compare = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(countries: DetalleContrato[], column: SortColumn, direction: string): DetalleContrato[] {
  if (direction === '' || column === '') {
    return countries;
  } else {
    return [...countries].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

// function matches(country: DetalleContrato, term: string, pipe: PipeTransform) {
//   return country.fechaSuscripcion.toLowerCase().includes(term.toLowerCase())
//     || country.fechaVigencia.toLowerCase().includes(term.toLowerCase())
//     || country.descripcion.toLowerCase().includes(term.toLowerCase())
//     || pipe.transform(country.numero).includes(term)
//     || pipe.transform(country.volumenEjecutado).includes(term);
// }

// function matches(country: DetalleContrato, term: string, pipe: PipeTransform) {
//   return country.fechaSuscripcion.toLowerCase().includes(term.toLowerCase())
//     || country.fechaVigencia.toLowerCase().includes(term.toLowerCase())
//     || country.descripcion.toLowerCase().includes(term.toLowerCase())
//     || pipe.transform(country.numero).includes(term)
//     || pipe.transform(country.volumenEjecutado).includes(term);
// }

function matches(country: DetalleContrato, term: string, pipe: PipeTransform) {
  return country.descripcion.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(country.numero).includes(term);
}


@Injectable({
  providedIn: 'root'
})
export class DetalleContratosService {
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _countries$ = new BehaviorSubject<DetalleContrato[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 12,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  contratosTransporte!: DetalleContrato[];

  gestionActual = 2021

  private buildDate(arr: number[]){
    var built = "";
    if (arr && arr.length==3){
      var zero = '00';
      var year = arr[0].toString();
      var month = zero.concat(arr[1].toString());
      month = month.substr(month.length-2, month.length-1);
      var day = zero.concat(arr[2].toString());
      day = day.substr(day.length-2, day.length-1);
      built = day+'/'+month+'/'+year;
    }
    return built;
  }

  constructor(private contratosService: ContratosService, private pipe: DecimalPipe) {

    this.getAllContracts()

    this._search$.pipe(
      tap(() => this._loading$.next(true)),
      debounceTime(200),
      switchMap(() => this._search()),
      delay(200),
      tap(() => this._loading$.next(false))
    ).subscribe(result => {
      this._countries$.next(result.countries);
      this._total$.next(result.total);
    });

    this._search$.next();
  }      

  private getAllContracts(){
    this.contratosTransporte=[];
    this.contratosService.obtenerTodosContratos(this.gestionActual)
    .subscribe(
      result => {
        var contratos:any = result; 
        if (contratos.success && contratos.data) {
          this.contratosTransporte = contratos.data
            .sort((a:any, b:any)=>{if (a.contrato.informacionGeneral.fechaSuscripcion<b.contrato.informacionGeneral.fechaSuscripcion) return -1; return 1})
            .map((element: any)=>({
              id: element.id,
              fechaSuscripcion: (element.contrato && element.contrato.informacionGeneral)?this.buildDate(element.contrato.informacionGeneral.fechaSuscripcion):"",
              fechaVigencia: (element.contrato && element.contrato.vigencia)?this.buildDate(element.contrato.vigencia.fechaFinalizacion):"",
              descripcion: (element.contrato && element.contrato.informacionEspecifica)?element.contrato.informacionEspecifica.descripcionContrato:'',
              pais: 'rare',
              numero: (element.contrato && element.contrato.informacionGeneral)?element.contrato.informacionGeneral.numeroContrato:0,
              volumenEjecutado: (element.contrato && element.contrato.nominacionVolumen)?element.contrato.nominacionVolumen.volumenEjecutado:0,
              saldoAdjudicado: (element.contrato)?element.contrato.totalVolumenesMonto.monto.saldo:0,
              tipo: element.contrato.informacionEspecifica.tipo, 
              ambito: element.contrato.informacionEspecifica.ambito,
              medio: element.contrato.informacionEspecifica.medio
            }))
        }
    },
    error => {console.log('Retrieving problems!');}
    );    
  }

  get countries$() { return this._countries$.asObservable(); }
  get total$() { return this._total$.asObservable(); }
  get loading$() { return this._loading$.asObservable(); }
  get page() { return this._state.page; }
  get pageSize() { return this._state.pageSize; }
  get searchTerm() { return this._state.searchTerm; }

  set page(page: number) { this._set({page}); }
  set pageSize(pageSize: number) { this._set({pageSize}); }
  set searchTerm(searchTerm: string) { this._set({searchTerm}); }
  set sortColumn(sortColumn: SortColumn) { this._set({sortColumn}); }
  set sortDirection(sortDirection: SortDirection) { this._set({sortDirection}); }

  private _set(patch: Partial<State>) {
    Object.assign(this._state, patch);
    this._search$.next();
  }

  private _search(): Observable<SearchResult> {
    const {sortColumn, sortDirection, pageSize, page, searchTerm} = this._state;

    // 1. sort
    let countries = sort(this.contratosTransporte, sortColumn, sortDirection);
    
    // 2. filter
    countries = countries.filter(country => matches(country, searchTerm, this.pipe));
    const total = countries.length;

    // 3. paginate
    countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({countries, total});
  }
}
