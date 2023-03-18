import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { Profile } from 'src/app/models/profile.interface';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-singup',
  templateUrl: './singup.component.html',
  styleUrls: ['./singup.component.scss']
})
export class SingupComponent implements OnInit {

  singupForm: Profile | undefined
  // singupForm: FormGroup = new FormGroup({
  //   name: new FormControl(),
  //   surname: new FormControl(),
  //   username: new FormControl(),
  //   email: new FormControl(),
  //   password: new FormControl()
  // });

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(singupForm: Profile): void {
    this.auth.singup(singupForm)
    // this.router.navigate(["/"])
  }

}
