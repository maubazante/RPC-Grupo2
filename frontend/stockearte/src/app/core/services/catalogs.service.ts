import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Catalogo, CatalogoSOAP } from '../../shared/types/Catalogo';
import { environment } from '../../../environments/environment';

const BASE_URL = '/api/catalogos';

@Injectable({
  providedIn: 'root'
})
export class CatalogosService {
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) {}

  getCatalogos(username: string | null): Observable<Catalogo[]> {
    return this.http.get<Catalogo[]>(`${environment.soapSysURL}${BASE_URL}?username=${username}`);
  }

  getCatalogoById(id: number): Observable<Catalogo> {
    return this.http.get<Catalogo>(`${environment.soapSysURL}${BASE_URL}/${id}`);
  }

  createCatalogo(catalogoCatalogo: Catalogo, username: string | null): Observable<Catalogo> {
    console.log(catalogoCatalogo);
    return this.http.post<Catalogo>(`${environment.soapSysURL}${BASE_URL}?username=${username}`, catalogoCatalogo, { headers: this.headers });
  }

  updateCatalogo(id: number, username: string | null, catalogoCatalogo: CatalogoSOAP): Observable<Catalogo> {
    return this.http.put<Catalogo>(`${environment.soapSysURL}${BASE_URL}/${id}?username=${username}`, catalogoCatalogo, { headers: this.headers });
  }

  deleteCatalogo(id: number, username: string | null): Observable<void> {
    return this.http.delete<void>(`${environment.soapSysURL}${BASE_URL}/${id}?username=${username}`, { headers: this.headers });
  }

  exportCatalogoToPDF(id: number, username: string | null): Observable<Blob> {
    return this.http.get(`${environment.soapSysURL}${BASE_URL}/exportar/pdf/${id}?username=${username}`, {
      responseType: 'blob'
    });
  }
}
