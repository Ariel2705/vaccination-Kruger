import { RouterModule } from "@angular/router";
import { LoginComponent } from "./components/login/login.component";
import { AdminComponent } from "./components/admin/admin.component";
import { EmpleadoComponent } from "./components/empleado/empleado.component";

const appRoutes = [
  { path: "", component: LoginComponent, pathMatch: "full" },
  { path: "admin", component: AdminComponent, pathMatch: "full" },
  { path: "empleado", component: EmpleadoComponent, pathMatch: "full" }
];
export const routing = RouterModule.forRoot(appRoutes);