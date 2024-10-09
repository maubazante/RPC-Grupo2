import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { ITiendaRequest } from '../../shared/types/ITiendaRequest';
import { Tienda, TiendaArray } from '../../shared/types/Tienda';
import { IPutUserResponse } from '../../shared/types/IUsuarioResponse';

@Injectable({
  providedIn: 'root'
})
export class StoresService {

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  createStore(storeData: ITiendaRequest): Observable<any> {
    return this.http.post(`${environment.clientURL}/createTienda`, storeData);
  }

  deleteStore(codigo: string): Observable<any> {
    return this.http.delete(`${environment.clientURL}/deleteTienda`, { body: { codigo } });
  }

  modifyStore(storeData: Tienda): Observable<IPutUserResponse> {
    return this.http.put<IPutUserResponse>(`${environment.clientURL}/modifyTienda`, storeData);
  }

  // búsqueda
  findTiendas(codigo: string): Observable<any> {
    return this.http.post(`${environment.clientURL}/findTiendas`, { body: { codigo } });
  }


  getTiendas(usernameParam: string | null, habilitadas: boolean): Observable<TiendaArray> {
    return this.http.get<TiendaArray>(`${environment.clientURL}/getTiendas?username=${usernameParam}&habilitadas=${habilitadas}`);
  }
}
