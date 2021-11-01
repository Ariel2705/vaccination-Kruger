import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from "rxjs/Observable";

const headers = new HttpHeaders().set('Authorization', String(localStorage.getItem('token') || ''));

@Injectable({
  providedIn: "root"
})
export class DetailsVaccination {
  constructor(private http: HttpClient) { }

  findDetailByCodEmployee(code: any): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/detailVaccination/findDetailByCodEmployee/" + code, { headers: headers });
  }

  findByTypeVaccination(type: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/employee/findEmployeeByTypeVaccine/" + type, { headers: headers });
  }

  findEmployeeByDates(dates: any): Observable<any> {
    const params = JSON.stringify(dates);
    return this.http.post("http://localhost:8080/api/kruger/employee/findEmployeeByDates", params, { headers: headers });
  }
  
  createDetailVaccination(detailVaccination: any, identification:string): Observable<any> {
    return this.http.post("http://localhost:8080/api/kruger/detailVaccination/createDetailVaccination/"+identification, detailVaccination, { headers: headers });    
  }
}