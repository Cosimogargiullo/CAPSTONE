import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login/login.component';
import { ResetComponent } from './reset/reset.component';
import { SingupComponent } from './singup/singup.component';

const routes: Routes = [
  {
    path: 'guest',
    component: AuthComponent,
    children: [
      {
        path: '',
        loadChildren: () =>
          import('./homepage/homepage.module').then((mod) => mod.HomepageModule),
      },
      {
        path: 'login',
        loadChildren: () =>
          import('./login/login.module').then((mod) => mod.LoginModule),
      },
      {
        path: 'singup',
        loadChildren: () =>
          import('./singup/singup.module').then((mod) => mod.SingupModule),
      },
      {
        path: 'forgot/password',
        loadChildren: () =>
          import('./forgot-password/forgot-password.module').then((mod) => mod.ForgotPasswordModule),
      },
      {
        path: 'reset/password/:id',
        loadChildren: () =>
          import('./reset/reset.module').then((mod) => mod.ResetModule),
      }
    ]

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
