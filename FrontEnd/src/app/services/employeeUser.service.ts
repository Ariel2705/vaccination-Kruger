import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";

@Injectable({
  providedIn: "root"
})
export class EmployeeUser {
  constructor(private http: HttpClient) {}

  login(user: any): Observable<any> {
    return this.http.post("http://localhost:8080/api/kruger/security/login", user);
  }
}