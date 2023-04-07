import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { ProductsViewComponent } from './products-view.component';

const routes: Routes = [
  {
    path: "",
    component: ProductsViewComponent
  },
  {
    path: "product/details/:id",
    component: ProductDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsViewRoutingModule { }
