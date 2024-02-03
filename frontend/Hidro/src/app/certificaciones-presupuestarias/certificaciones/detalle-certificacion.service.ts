import {Injectable, PipeTransform} from '@angular/core';
import {DecimalPipe} from '@angular/common';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';

import {SortColumn, SortDirection} from './sortable.directive';
import { CertificacionesService } from "../../certificaciones.service";
import { DetalleCertificacion } from '../interfaces-presupuesto';

interface SearchResult {
  countries: DetalleCertificacion[]; 
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

function sort(countries: DetalleCertificacion[], column: SortColumn, direction: string): DetalleCertificacion[] {
  if (direction === '' || column === '') {
    return countries;
  } else {
    return [...countries].sort((a, b) => {
      const res = compare(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

function matches(country: DetalleCertificacion, term: string, pipe: PipeTransform) {
  return country.concepto.toLowerCase().includes(term.toLowerCase())
    || country.tipoServicio.toLowerCase().includes(term.toLowerCase())
    || country.fechaEmision.toLowerCase().includes(term.toLowerCase())
    || country.numeroCertificacion.toLowerCase().includes(term.toLowerCase())
    || country.descripcion.toLowerCase().includes(term.toLowerCase())
    || pipe.transform(country.montoMaximo).includes(term)
    || pipe.transform(country.montoEjecutado).includes(term)
    || pipe.transform(country.saldo).includes(term)    
}

@Injectable({
  providedIn: 'root'
})
export class DetalleCertificacionService {
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _countries$ = new BehaviorSubject<DetalleCertificacion[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 12,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  certificacionesPresupuestarias!: DetalleCertificacion[];

  constructor(private certificacionesService: CertificacionesService, 
              private pipe: DecimalPipe) {

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
    this.certificacionesPresupuestarias=[];
    this.certificacionesService.obtenerTodasCertificaciones()
    .subscribe(
      result => {
        var certis:any = result; 
        if (certis.success && certis.data) {
          this.certificacionesPresupuestarias = certis.data.certificaciones
            // .sort((a:any, b:any)=>{if (a.contrato.informacionGeneral.fechaSuscripcion<b.contrato.informacionGeneral.fechaSuscripcion) return -1; return 1})
            .map((element: any)=>({
              id: element.id,
              concepto: element.concepto,
              idPartida: element.idPartida,
              categoria: element.categoria,
              fechaEmision: element.fechaEmision,
              numeroCertificacion: element.numeroCertificacion,
              descripcion: element.descripcion,
              montoMaximo: element.montoMaximo,
              montoEjecutado: element.montoEjecutado,
              saldo: element.saldo,
              tipoServicio: element.tipoServicio
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
    let countries = sort(this.certificacionesPresupuestarias, sortColumn, sortDirection);
    
    // 2. filter
    countries = countries.filter(country => matches(country, searchTerm, this.pipe));
    const total = countries.length;

    // 3. paginate
    countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({countries, total});
  }
}
