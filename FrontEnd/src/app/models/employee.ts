export class EmployeeModel {
    constructor(
        public identification: string,
        public names: string,
        public surnames: string,
        public email: string,
        public state: string,
    ){}
}