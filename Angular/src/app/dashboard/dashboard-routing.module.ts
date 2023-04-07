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
          import('./../auth/homepage/homepage.module').then((mod) => mod.HomepageModule),
      },
      {
        path:'profile',
        loadChildren: () =>
          import('./profile/profile.module').then((mod) => mod.ProfileModule),
      },
      {
        path:'shop',
        loadChildren: () =>
          import('./shop/shop.module').then((mod) => mod.ShopModule),
      },
      {
        path:'cart',
        loadChildren: () =>
          import('./cart/cart.module').then((mod) => mod.CartModule),
      },
      {
        path:'checkout',
        loadChildren: () =>
          import('./checkout/checkout.module').then((mod) => mod.CheckoutModule),
      },
      {
        path:'orders',
        loadChildren: () =>
          import('./order/order.module').then((mod) => mod.OrderModule),
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
