import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  emailString : string | undefined
  constructor(private auth : AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(email:{email: string}): void{
    this.emailString =  email.email
    this.auth.forgotPassword(email).subscribe()
  }

}
