import { Component, OnInit } from '@angular/core';

import { Employee } from "../../services/employee.service";

import { EmployeeModel } from '../../models/employee';

@Component({
  selector: 'app-employee-create',
  templateUrl: './employee-create.component.html',
  styleUrls: ['./employee-create.component.css']
})
export class EmployeeCreateComponent implements OnInit {

  public employee: EmployeeModel;
  public status: String;

  constructor(
    public employeeService: Employee
    ) {
    this.employee = new EmployeeModel("","","","","");
  }

  ngOnInit(): void {
  }

  onSubmit(employeeForm: any) : void {
    this.employeeService.createEmployee(this.employee).subscribe(
      response => {
        employeeForm.reset();
        this.status = 'success';
        this.employee = new EmployeeModel("","","","","");
      },
      error => {
        console.log(error);        
        this.status = 'failed';
      }
    );
  }

}
