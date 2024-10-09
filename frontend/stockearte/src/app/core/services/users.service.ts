import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IUsuarioRequest } from '../../shared/types/IUsuarioRequest';
import { Usuario, UsuariosArray } from '../../shared/types/Usuario';
import { IPutUserResponse } from '../../shared/types/IUsuarioResponse';
import { ILoginResponse } from '../../shared/types/ILoginResponse';
import { ILoginRequest } from '../../shared/types/ILoginRequest';
import { IRegisterRequest } from '../../shared/types/IRegisterRequest';
import { IRegisterResponse } from '../../shared/types/IRegisterResponse';

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

  getUsers(habilitados: boolean): Observable<UsuariosArray> {
    return this.http.get<UsuariosArray>(`${environment.clientURL}/getUsuarios?habilitados=${habilitados}`);
  }

  loginUsuario(request: ILoginRequest): Observable<ILoginResponse> {
    return this.http.post<ILoginResponse>(`${environment.clientURL}/login`, request);
  }

  registerUsuario(request: IRegisterRequest): Observable<IRegisterResponse> {
    return this.http.post<IRegisterResponse>(`${environment.clientURL}/createUsuario`, request);
  }
}
