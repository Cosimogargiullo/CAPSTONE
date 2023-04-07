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
        document.getElementById("notValid")?.classList.remove("d-none")
        document.getElementById("card")?.classList.add("d-none")
      }
    })
  }

  onSubmit(password: {password: string}): void {
    this.auth.resetPassword(this.profile!.id, password)
    document.getElementById("modal")?.classList.remove("d-none")
        setTimeout(()=>{
          this.router.navigate(["/guest/login"])
      }, 1700);
  }

}
