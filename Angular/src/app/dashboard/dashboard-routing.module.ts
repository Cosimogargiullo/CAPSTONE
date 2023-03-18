import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard.component';


const routes: Routes = [
  {
    path: '',                               //passage route from which you can see the movies component as home only if you are logged
    component: DashboardComponent,
    children: [
      {
        path:'',
        loadChildren: () =>
          import('./profile/profile.module').then((mod) => mod.ProfileModule),
      }
      // ,
      // {
      //   path:'',
      //   loadChildren:()=>
      //     import('./movies/movies.module').then((mod)=>mod.MoviesModule)
      // }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
