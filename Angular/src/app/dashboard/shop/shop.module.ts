import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ShopRoutingModule } from './shop-routing.module';
import { ProductsViewComponent } from './products-view/products-view.component';
import { ProductCreateComponent } from './product-create/product-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ProductsViewComponent,
    ProductCreateComponent
  ],
  imports: [
    CommonModule,
    ShopRoutingModule, FormsModule, ReactiveFormsModule
  ]
})
export class ShopModule { }
