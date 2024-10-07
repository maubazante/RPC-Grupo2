import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class OrderService {
    
    getAllOrders() {
        throw new Error('Method not implemented.');
    }
    createOrder(result: any) {
        throw new Error('Method not implemented.');
    }
    modifyOrder(order: any) {
        throw new Error('Method not implemented.');
    }
    deleteOrder(id: string) {
        throw new Error('Method not implemented.');
    }

    constructor(private http: HttpClient) { }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
        }),
    };


}