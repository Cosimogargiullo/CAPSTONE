import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PasswordComponent } from './password/password.component';
import { ProfileComponent } from './profile.component';

const routes: Routes = [
  {
    path: '',                               //passage route from which you can see the movies component as home only if you are logged
    component: ProfileComponent,
    children: [
      {
        path:'',
        loadChildren: () =>
          import('./info/info.module').then((mod) => mod.InfoModule),
      },
      {
        path:'password',
        loadChildren:()=>
          import('./password/password.module').then((mod)=>mod.PasswordModule)
      },
      {
        path:'security',
        loadChildren:()=>
          import('./security-component/security-component.module').then((mod)=>mod.SecurityComponentModule)
      },
      {
        path:'shop',
        loadChildren:()=>
          import('./shop/shop.module').then((mod)=>mod.ShopModule)
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
