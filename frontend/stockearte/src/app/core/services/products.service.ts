import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IProductoRequest } from '../../shared/types/IProductoRequest';
import { UsuariosArray } from '../../shared/types/Usuario';
import { Producto, ProductoArray } from '../../shared/types/Producto';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  createProduct(productData: IProductoRequest): Observable<any> {
    return this.http.post(`${environment.clientURL}/createProducto`, productData);
  }

  deleteProduct(productId: string): Observable<any> {
    return this.http.delete(`${environment.clientURL}/deleteProducto`, { body: { productoId: productId } });
  }

  modifyProduct(productData: Producto): Observable<any> {
    return this.http.put(`${environment.clientURL}/modifyProducto`, productData);
  }

  // búsqueda
  findProductos(productos: string): Observable<any> {
    return this.http.post(`${environment.clientURL}/findProductos`, { body: { productos } });
  }

  getProductos(usernameParam: string): Observable<ProductoArray> {
    let body = { username: usernameParam }
    return this.http.post<ProductoArray>(`${environment.clientURL}/getProductos`, body);
  }
}
