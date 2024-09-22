import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IUsuarioRequest } from '../../shared/types/IUsuarioRequest';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  createUser(userData: IUsuarioRequest): Observable<any> {
    return this.http.post(`${environment.clientURL}/createUsuario`, userData);
  }

  deleteUser(userId: number): Observable<any> {
    return this.http.delete(`${environment.clientURL}/deleteUsuario`, { body: { usuarioId: userId } });
  }

  modifyUser(userData: IUsuarioRequest): Observable<any> {
    return this.http.put(`${environment.clientURL}/modifyUsuario`, userData);
  }
}
