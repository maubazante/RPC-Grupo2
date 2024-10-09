import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { Novedad } from "../../shared/types/Novedad";
import { environment } from "../../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class NewsService {

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  getAllNews(): Observable<Novedad[]> {
    return this.http.get<Novedad[]>(`${environment.controllerURL}/api/novedades`);
  }

  deleteNews(id: string): Observable<void> {
    return this.http.delete<void>(`${environment.controllerURL}/${id}`);
  }

  createNews(novedad: Novedad): Observable<Novedad> {
    return this.http.post<Novedad>(`${environment.controllerURL}`, novedad);
  }

  modifyNews(novedad: any): Observable<string> {
    return this.http.put<string>(`${environment.controllerURL}/api/novedades`, novedad);
  }

}