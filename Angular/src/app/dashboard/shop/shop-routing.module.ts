import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShopComponent } from './shop.component';

const routes: Routes = [
  {
    path: '',
    component: ShopComponent,
    children: [
      {
        path:'',
        loadChildren: () =>
          import('./products-view/products-view-routing.module').then((mod) => mod.ProductsViewRoutingModule),
      },
      {
        path:'create/product',
        loadChildren: () =>
          import('./product-create/product-create-routing.module').then((mod) => mod.ProductCreateRoutingModule),
      }
    ],
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }
