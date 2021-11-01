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
export class Employee {
  constructor(private http: HttpClient) { }

  createEmployee(employee: EmployeeModel): Observable<any> {
    const params = JSON.stringify(employee);
    console.log(params)
    return this.http.post("http://localhost:8080/api/kruger/employee/createEmployee", params, { headers: headers });
  }

  findByStateVaccination(state: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/employee/findEmployeeByState/" + state, { headers: headers });
  }

  findByTypeVaccination(type: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/employee/findEmployeeByTypeVaccine/" + type, { headers: headers });
  }

  findEmployeeByDates(dates: any): Observable<any> {
    const params = JSON.stringify(dates);
    return this.http.post("http://localhost:8080/api/kruger/employee/findEmployeeByDates", params, { headers: headers });
  }

  findEmployeeById(identification: string): Observable<any> {
    return this.http.get("http://localhost:8080/api/kruger/employee/findEmployeeById/" + identification, { headers: headers });
  }

  completeDataEmployee(completeData: any): Observable<any> {
    return this.http.put("http://localhost:8080/api/kruger/employee/completeDataEmployee", completeData, {headers: headers});
  }
  
  changeStateEmployee(identification: string): Observable<any> {
    return this.http.put("http://localhost:8080/api/kruger/employee/changeStateEmployee/" + identification, null,  {headers: headers});
  }

  findByDates(dates: any): Observable<any> {
    return this.http.post("http://localhost:8080/api/kruger/employee/findEmployeeByDates", dates, { headers: headers }) ;
  }

}