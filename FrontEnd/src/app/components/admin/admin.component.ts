import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

import { Employee } from "../../services/employee.service";

import { EmployeeDetailsComponent } from '../employee-details/employee-details.component';
import { EmployeeCreateComponent } from '../employee-create/employee-create.component';

import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements AfterViewInit{
  public title: string;
  public options: string;
  public stateVaccination: string;
  public typeVaccine: string;
  public initialDate: string;
  public finalDate: string;
  displayedColumns: string[];
  dataSourceEmployee = new MatTableDataSource();
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(
    public employeeService: Employee,
    public dialog: MatDialog,
    public router: Router
    ) {
    this.title = 'Listado de empleados';
    this.options = '1';
  }

  ngAfterViewInit(): void {
    this.dataSourceEmployee.paginator = this.paginator;
    this.dataSourceEmployee.sort = this.sort;
  }
  
  applyFilter(event: Event): any {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSourceEmployee.filter = filterValue.trim().toLowerCase();
    this.dataSourceEmployee.filterPredicate = (data: any, filter) => {
      const dataStr = JSON.stringify(data).toLowerCase();
      return dataStr.indexOf(filter) !== -1;
    };
  }

  openDialogUpdate(codigo: string): void {
  }

  openDialogDetails(employee: any): void {
    const dialogRef = this.dialog.open(EmployeeDetailsComponent);
    dialogRef.componentInstance.code = employee.id;
    dialogRef.componentInstance.identification = employee.identification;
    dialogRef.componentInstance.names = employee.names;
    dialogRef.componentInstance.surnames = employee.surnames;
    dialogRef.componentInstance.email = employee.email;
    dialogRef.componentInstance.birthdate = employee.birthdate;
    dialogRef.componentInstance.phone = employee.phone;

    dialogRef.afterClosed().subscribe();
  }

  openDialogCreate(): void {
    const dialogRef = this.dialog.open(EmployeeCreateComponent);
    dialogRef.afterClosed().subscribe();
  }

  onSubmit(): void {
    var employee: any[] = [];
    if (this.options === '1') {
      this.employeeService.findByStateVaccination(this.stateVaccination).subscribe(
        res => {
          this.dataSourceEmployee.data = res;
        },
        error => {
          console.error(error);    
          this.dataSourceEmployee.data = [];
        }
      );
    } else if (this.options === '2') {
      this.employeeService.findByTypeVaccination(this.typeVaccine).subscribe(
        res => {
          this.dataSourceEmployee.data = res;
        },
        error => {
          console.error(error);    
          this.dataSourceEmployee.data = [];
        }
      );
    } else if (this.options === '3') {      
      const dates = {initialDate: this.initialDate, finalDate: this.finalDate};
      this.employeeService.findByDates(dates).subscribe(
        res => {
          this.dataSourceEmployee.data = res;
        },
        error => {
          console.error(error);    
          this.dataSourceEmployee.data = [];
        }
      );
    }

    this.displayedColumns = ['identification', 'names', 'surnames', 'phone', 'stateVaccination', 'actionsClientList'];
  }

  exit(): void {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/');
  }

  deleteEmployee(identification: string): void {
    this.employeeService.changeStateEmployee(identification).subscribe(
      (response: any) => {
        console.log(response);
      }, (error: any) => {
        console.log(error);
      }
    )
  }

}
