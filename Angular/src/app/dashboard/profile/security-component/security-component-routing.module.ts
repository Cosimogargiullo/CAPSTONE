import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SecurityComponentComponent } from './security-component.component';

const routes: Routes = [{
  path:'',
  component:SecurityComponentComponent,
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SecurityComponentRoutingModule { }
