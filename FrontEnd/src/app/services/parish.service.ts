import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs/Observable";

import { EmployeeModel } from '../models/employee';

const headers = new HttpHeaders()
  .set('Content-Type', 'application/json')
  .set('Authorization', String(localStorage.getItem('token') || ''));

@Injectable({
  providedIn: "root"
})
export class Parish {
  constructor(private http: HttpClient) { }

  findParishesByCanton(canton: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/parish/findParishByCanton/" + canton, { headers: headers });
  }


}