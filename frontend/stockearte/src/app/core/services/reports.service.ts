import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Filtro } from '../../shared/types/Filtro';
import { environment } from '../../../environments/environment';

@Injectable({
    providedIn: 'root',
})
export class ReportsService {
    private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    constructor(private http: HttpClient) { }

    agregarFiltro(filtro: Filtro) {
        return this.http.post(`${environment.soapSysURL}/api/filtroOrdenCompra`, filtro, { headers: this.headers });
    }

    modificarFiltro(id: number, filtro: Filtro) {
        return this.http.put(`${environment.soapSysURL}/api/filtroOrdenCompra/${id}`, filtro, { headers: this.headers });
    }

    eliminarFiltro(id: number) {
        return this.http.delete(`${environment.soapSysURL}/api/filtroOrdenCompra`, { headers: this.headers });
    }

    obtenerFiltros(userId: string | null) {
        return this.http.get(`${environment.soapSysURL}/api/filtroOrdenCompra/list/${userId}`, { headers: this.headers });
    }

    getReports() {
        return this.http.get(`${environment.controllerURL}/api/ordenDeCompra/list`);
    }
}
