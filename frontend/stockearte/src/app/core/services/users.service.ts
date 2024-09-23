import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IUsuarioRequest } from '../../shared/types/IUsuarioRequest';
import { Usuario, UsuariosArray } from '../../shared/types/Usuario';
import { IPutUserResponse } from '../../shared/types/IUsuarioResponse';

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

  deleteUser(userId: string): Observable<any> {
    return this.http.delete(`${environment.clientURL}/deleteUsuario`, { body: { usuarioId: userId } });
  }

  modifyUser(userData: Usuario): Observable<IPutUserResponse> {
    return this.http.put<IPutUserResponse>(`${environment.clientURL}/modifyUsuario`, userData);
  }

  // búsqueda
  findUsers(username: string): Observable<any> {
    return this.http.post(`${environment.clientURL}/findUsuarios`, { body: { username } });
  }


  getUsers(): Observable<UsuariosArray> {
    return this.http.get<UsuariosArray>(`${environment.clientURL}/getUsuarios`);
  }
}
