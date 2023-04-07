import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthModule } from './auth/auth.module';
import { InfoModule } from './dashboard/profile/info/info.module';
import { PasswordModule } from './dashboard/profile/password/password.module';
import { SecurityComponentModule } from './dashboard/profile/security-component/security-component.module';
import { ForgotPasswordModule } from './auth/forgot-password/forgot-password.module';
import { LoginModule } from './auth/login/login.module';
import { ResetModule } from './auth/reset/reset.module';
import { SingupModule } from './auth/singup/singup.module';
import { ShopModule } from './dashboard/shop/shop.module';
import { ProductsViewModule } from './dashboard/shop/products-view/products-view.module';
import { ProductCreateModule } from './dashboard/shop/product-create/product-create.module';
import { ProductDetailsModule } from './dashboard/shop/products-view/product-details/product-details.module';
import { CartModule } from './dashboard/cart/cart.module';
import { CheckoutModule } from './dashboard/checkout/checkout.module';
import { OrderModule } from './dashboard/order/order.module';


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    HttpClientModule,
    InfoModule,
    PasswordModule,
    SecurityComponentModule,
    ForgotPasswordModule,
    LoginModule,
    ResetModule,
    SingupModule,
    ShopModule,
    ProductsViewModule,
    ProductCreateModule,
    ProductDetailsModule,
    CartModule,
    CheckoutModule,
    OrderModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
