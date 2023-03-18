import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Profile } from '../models/profile.interface';
import { UserClass } from '../models/user-class';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  ngOnInit(): void {
  }

}
