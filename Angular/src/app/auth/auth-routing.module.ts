import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login/login.component';
import { ResetComponent } from './reset/reset.component';
import { SingupComponent } from './singup/singup.component';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent

  }
  ,
  {
    path: 'singup',
    component:SingupComponent

  },
  {
    path: 'forgot/password',
    component:ForgotPasswordComponent

  },
  {
    path: 'reset/password/:token',
    component:ResetComponent

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
