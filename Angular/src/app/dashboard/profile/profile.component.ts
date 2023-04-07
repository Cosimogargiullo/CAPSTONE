import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  constructor(private authSrv: AuthService) { }

  user : UserClass | undefined
  profile: Profile | undefined

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.authSrv.getUserById(this.user?.id).subscribe(e=>{
      this.profile = e
      console.log(e)})
  }

}
