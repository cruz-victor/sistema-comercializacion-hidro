import {Injectable, PipeTransform} from '@angular/core';
import {DecimalPipe} from '@angular/common';

import {BehaviorSubject, Observable, of, Subject} from 'rxjs';
import {debounceTime, delay, switchMap, tap} from 'rxjs/operators';

import {SortColumn, SortDirection} from './sortable.directive';
import { CertificacionesService } from "../../certificaciones.service";
import { DetallePartida } from '../interfaces-presupuesto';

interface SearchResult {
  countries: DetallePartida[];
  total: number;
}

interface State {
  page: number;
  pageSize: number;
  searchTerm: string;
  sortColumn: SortColumn;
  sortDirection: SortDirection
}

const comparing = (v1: string | number, v2: string | number) => v1 < v2 ? -1 : v1 > v2 ? 1 : 0;

function sort(countries: DetallePartida[], column: SortColumn, direction: string): DetallePartida[] {
  if (direction === '' || column === '') {
    return countries;
  } else {
    return [...countries].sort((a, b) => {
      const res = comparing(a[column], b[column]);
      return direction === 'asc' ? res : -res;
    });
  }
}

function matches(country: DetallePartida, term: string, pipe: PipeTransform) {
  return country.numeroPartida.toLowerCase().includes(term.toLowerCase())
    || country.categoria.toLocaleLowerCase().includes(term.toLocaleLowerCase())
    || pipe.transform(country.montoActual).includes(term)
    || pipe.transform(country.montoAprobado).includes(term)
    || pipe.transform(country.montoIncremento).includes(term)
    || pipe.transform(country.saldo).includes(term)
}

@Injectable({
  providedIn: 'root'
})
export class DetallePartidaService {
  private _loading$ = new BehaviorSubject<boolean>(true);
  private _search$ = new Subject<void>();
  private _countries$ = new BehaviorSubject<DetallePartida[]>([]);
  private _total$ = new BehaviorSubject<number>(0);

  private _state: State = {
    page: 1,
    pageSize: 12,
    searchTerm: '',
    sortColumn: '',
    sortDirection: ''
  };

  partidasPresupuestarias!: DetallePartida[];

  constructor(private certificacionesService: CertificacionesService, 
              private pipe: DecimalPipe) {

    this.getAllPartidas()

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

  private getAllPartidas(){
    this.partidasPresupuestarias=[];
    this.certificacionesService.obtenerPartidas()
    .subscribe(
      result => {
        var partidas:any = result; 
        if (partidas.success && partidas.data) {
          this.partidasPresupuestarias = partidas.data.partidas
            .sort((a:any, b:any)=>{if (a.numeroPartida<b.numeroPartida) return -1; return 1})
            .map((element: any)=>({
              id: element.id,
              categoria: element.categoria,
              numeroPartida: element.numeroPartida,
              montoActual: element.montoActual,
              montoAprobado: element.montoAprobado,
              montoIncremento: element.montoIncremento,
              saldo: element.saldo
              // rutaDocumentoAdjunto: element.rutaDocumentoAdjunto
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
    let countries = sort(this.partidasPresupuestarias, sortColumn, sortDirection);
    
    // 2. filter
    countries = countries.filter(country => matches(country, searchTerm, this.pipe));
    const total = countries.length;

    // 3. paginate
    countries = countries.slice((page - 1) * pageSize, (page - 1) * pageSize + pageSize);
    return of({countries, total});
  }
}
