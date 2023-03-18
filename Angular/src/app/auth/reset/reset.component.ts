import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from 'src/app/models/profile.interface';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-reset',
  templateUrl: './reset.component.html',
  styleUrls: ['./reset.component.scss']
})
export class ResetComponent implements OnInit {
  profile: Profile | undefined
  token: string | undefined
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.token = window.location.href.split('/').pop();
    this.auth.getUserbyResetToken(this.token!).subscribe(e => {
      this.profile = e
      console.log(this.profile)
      if (this.profile! == null) {
        this.router.navigate(["/"])
      }
    })
  }

  onSubmit(password: {password: string}): void {
    console.log(password)
    this.auth.resetPassword(this.profile!.id, password)
    this.router.navigate(["/"])
  }

}
