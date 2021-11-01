import { Component } from '@angular/core';
import { EmployeeUser } from "../../services/employeeUser.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  username: string;
  password: string;

  constructor(public employeeUser: EmployeeUser, public router: Router) {}

  login(): void {
    const user = {username: this.username, pwd: this.password};
    this.employeeUser.login(user).subscribe( (data: any) => {
      localStorage.setItem('token', data.token);
      localStorage.setItem('username', data.user);
      if(data.role === 'ADM'){
        this.router.navigateByUrl('/admin');
      } else {
        this.router.navigateByUrl('/empleado');
      }
    });
  }

}
