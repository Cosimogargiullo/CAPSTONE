import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { RouterModule } from '@angular/router';
import { ProfileModule } from './profile/profile.module';
import { NavbarComponent } from './navbar/navbar.component';




@NgModule({
  declarations: [DashboardComponent, NavbarComponent],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    RouterModule, ProfileModule,
  ]
})
export class DashboardModule { }
