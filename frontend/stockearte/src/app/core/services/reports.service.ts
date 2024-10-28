import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Filtro } from '../../shared/types/Filtro';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class ReportsService {
    constructor(private http: HttpClient) {}
    httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    agregarFiltro(filtro: Filtro) {
        return this.http.post(`${environment.soapSysURL}/api/filtroOrdenCompra`, filtro);
    }

    modificarFiltro(id: number, filtro: Filtro) {
        return this.http.put(`${environment.soapSysURL}/api/filtroOrdenCompra`, filtro);
    }

    eliminarFiltro(id: number) {
        return this.http.delete(`${environment.soapSysURL}/api/filtroOrdenCompra/${id}`);
    }

    obtenerFiltros(userId: string | null) {
        return this.http.get(`${environment.soapSysURL}/api/filtroOrdenCompra/list/${userId}`);
    }

    getReports() {
        return this.http.get(`${environment.soapSysURL}/api/ordenDeCompra/list`);
    }
}
