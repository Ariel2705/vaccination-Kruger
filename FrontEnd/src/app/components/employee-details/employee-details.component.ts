import { Component, OnInit } from '@angular/core';

import { DetailsVaccination } from "../../services/detailsVaccination.service";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  public code: string;
  public identification : string;
  public names : string;
  public surnames : string;
  public email : string;
  public birthdate : string;
  public phone : string;
  public vaccineType : string;
  public vaccineDate : string;
  public vaccineDoses : string;

  constructor(
    public detailsVaccinationService: DetailsVaccination) { }

  ngOnInit(): void {
    this.detailsVaccinationService.findDetailByCodEmployee(this.code).subscribe(      
      response => {
        console.log(response);
        this.vaccineType = response[0].vaccineType;
        this.vaccineDate = response[0].vaccineDate;
        this.vaccineDoses = response[0].vaccineDoses;
      }
    );
  }

}
