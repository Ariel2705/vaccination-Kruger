import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Employee } from "../../services/employee.service";
import { Parish } from "../../services/parish.service";
import { DetailsVaccination } from "../../services/detailsVaccination.service";

@Component({
  selector: 'app-empleado',
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css']
})
export class EmpleadoComponent implements OnInit {

  public code: number;
  public identification: string;
  public names: string;
  public surnames: string;
  public email: string;
  public state: string;
  public stateVaccination: string;
  public stateVaccinationU: string;

  public phone: string;
  public codParish: string;
  public mainAddress: string;
  public sideStreet: string;
  public birthDate: string;

  public vaccineDoses: number;
  public dateVaccine: string;
  public typeVaccine: string;

  public statusUpdate: string;
  public parishes: any[];

  constructor(
    public employeeService: Employee,
    public parishService: Parish,
    public detailsService: DetailsVaccination,
    public router: Router
    ) {
    this.parishes = new Array<string>();
  }

  ngOnInit(): void {
    this.identification = String(localStorage.getItem('username') || '');
    this.employeeService.findEmployeeById(this.identification).subscribe(
      response => {
        this.email = response.email;
        this.code = response.id;
        this.names = response.names;
        this.surnames = response.surnames;
        this.birthDate = response.birthdate;
        this.phone = response.phone;
        this.stateVaccination = response.stateVaccination;
        if (this.stateVaccination === 'SI') {
          this.detailsService.findDetailByCodEmployee(this.code).subscribe(
            response => {
              console.log(response);
              this.typeVaccine = response[0].vaccineType;
              this.dateVaccine = response[0].vaccineDate;
              this.vaccineDoses = response[0].vaccineDoses;
            }
          );
        } else {
          this.parishService.findParishesByCanton("Quito").subscribe(
            response => {
              for (const item of response) {
                this.parishes.push(item);
              }
            }
          );
        }
      }
    );
  }

  onSubmit(form: any): void {
    const completeData = {
      identification: this.identification,
      birthdate: this.birthDate,
      codParish: this.codParish,
      phone: this.phone,
      mainAddress: this.mainAddress,
      sideStreet: this.sideStreet,
    };

    const detailVaccination = {
      codEmployee: "",
      vaccineType: this.typeVaccine,
      vaccineDate: this.dateVaccine,
      vaccineDoses: this.vaccineDoses,
    };

    this.employeeService.completeDataEmployee(completeData).subscribe(
      response => {
        this.detailsService.createDetailVaccination(detailVaccination, this.identification).subscribe(
          (response2: any) => {
            console.log(response2);
            this.statusUpdate = 'success';
          }, (error2: any) => {
            console.log(error2);
            this.statusUpdate = 'failed';
          });
        this.statusUpdate = 'success';
      },
      error => {
        console.log(error);
        this.statusUpdate = 'failed';
      });
    form.reset();
  }

  exit(): void {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/');
  }
}
