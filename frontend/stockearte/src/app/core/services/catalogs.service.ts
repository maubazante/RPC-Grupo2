import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Catalogo } from '../../shared/types/Catalogo';
import { environment } from '../../../environments/environment';

const BASE_URL = '/api/catalogos';

@Injectable({
  providedIn: 'root'
})
export class CatalogosService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getCatalogos(username: string | null): Observable<Catalogo[]> {
    return this.http.get<Catalogo[]>(`${environment.controllerURL}${BASE_URL}?username=${username}`);
  }

  getCatalogoById(id: number): Observable<Catalogo> {
    return this.http.get<Catalogo>(`${environment.soapSysURL}${BASE_URL}/${id}`);
  }

  createCatalogo(catalogoCatalogo: Catalogo): Observable<Catalogo> {
    return this.http.post<Catalogo>(`${environment.controllerURL}${BASE_URL}`, catalogoCatalogo, { headers: this.headers });
  }

  updateCatalogo(id: number, catalogoCatalogo: Catalogo): Observable<Catalogo> {
    return this.http.put<Catalogo>(`${environment.controllerURL}${BASE_URL}/${id}`, catalogoCatalogo, { headers: this.headers });
  }

  deleteCatalogo(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.controllerURL}${BASE_URL}/${id}`, { headers: this.headers });
  }

  exportCatalogoToPDF(id: number, username: string | null): Observable<Blob> {
    return this.http.get(`${environment.controllerURL}${BASE_URL}/exportar/pdf/${id}?username=${username}`, {
      responseType: 'blob'
    });
  }
}
