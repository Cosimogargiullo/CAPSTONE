import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Profile } from 'src/app/models/profile.interface';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm : Profile | undefined
  constructor(private authSrv: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(loginForm: Profile) {
    this.authSrv.login({ username: loginForm.username, password: loginForm.password }).subscribe(data => {
      console.log(data);
      this.authSrv.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
      localStorage.setItem('user', JSON.stringify(this.authSrv.user))
      this.router.navigate(["/"])
    });
  }

}
