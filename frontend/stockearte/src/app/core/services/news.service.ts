import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";


@Injectable({
    providedIn: 'root'
})
export class NewsService {
    getAllNews() {
      throw new Error('Method not implemented.');
    }
    createNews(selectedData: any) {
      throw new Error('Method not implemented.');
    }
    modifyNews(news: any) {
      throw new Error('Method not implemented.');
    }
    deleteNews(id: string) {
      throw new Error('Method not implemented.');
    }

    constructor(private http: HttpClient) { }

    httpOptions = {
        headers: new HttpHeaders({
            'Content-Type': 'application/json',
        }),
    };

}