import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../../../environments/environment";
import { OrdenDeCompra } from "../../shared/types/OrdenDeCompra";


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    
    
    constructor(private http: HttpClient) { }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
        }),
    };

    createOrder(productData: OrdenDeCompra): Observable<any> {
        return this.http.post(`${environment.controllerURL}/api/ordenDeCompra`, productData);
    }

    getAllOrders() {
        return this.http.get(`${environment.controllerURL}/api/ordenDeCompra/list`);
    }

    modifyOrder(order: any) {
        return this.http.put(`${environment.controllerURL}/api/ordenDeCompra/${order.id}`, order);
    }

    deleteOrder(id: string) {
        return this.http.delete(`${environment.controllerURL}/api/ordenDeCompra/${id}`);
    }

    marcarOrderComoRecibida(id: string): Observable<any> {
        return this.http.post(`${environment.controllerURL}/api/ordenDeCompra/marcarComoRecibida/${id}`, {});
    }
  
}