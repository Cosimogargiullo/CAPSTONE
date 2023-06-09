import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { PasswordComponent } from './password/password.component';
import { InfoComponent } from './info/info.component';
import { RouterModule } from '@angular/router';
import { InfoModule } from './info/info.module';
import { ProfileComponent } from './profile.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SecurityComponentComponent } from './security-component/security-component.component';
import { ShopComponent } from './shop/shop.component';


@NgModule({
  declarations: [ProfileComponent,
    PasswordComponent,
    InfoComponent,
    SecurityComponentComponent,
    ShopComponent
  ],
  imports: [
    CommonModule,
    ProfileRoutingModule, RouterModule, InfoModule,FormsModule, ReactiveFormsModule
  ]
})
export class ProfileModule { }
