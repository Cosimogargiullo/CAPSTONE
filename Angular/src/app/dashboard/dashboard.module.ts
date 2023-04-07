import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { RouterModule } from '@angular/router';
import { ProfileModule } from './profile/profile.module';
import { NavbarComponent } from './navbar/navbar.component';
import { ShopComponent } from './shop/shop.component';
import { CartComponent } from './cart/cart.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { OrderComponent } from './order/order.component';




@NgModule({
  declarations: [DashboardComponent, NavbarComponent, ShopComponent, CartComponent, CheckoutComponent, OrderComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    RouterModule, ProfileModule, FormsModule, ReactiveFormsModule,
  ]
})
export class DashboardModule { }
