import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { ITiendaRequest } from '../../shared/types/ITiendaRequest';

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

  modifyStore(storeData: ITiendaRequest): Observable<any> {
    return this.http.put(`${environment.clientURL}/modifyTienda`, storeData);
  }
}
