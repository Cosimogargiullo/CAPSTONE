import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from 'src/app/auth/auth.service';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss']
})
export class PasswordComponent implements OnInit {
  isEditable: boolean = true;
  passwordForm: {actualePassword: string, password: string, password2: string} | undefined

  constructor(private auth: AuthService) { }

  user: UserClass | undefined
  profile: Profile | undefined
  updateBtn: string = "update";

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.auth.getUserById(this.user?.id).subscribe(e => {
      this.profile = e
    })
  }

  edit(): void {
    this.isEditable = !this.isEditable
    document.getElementById("submit")!.classList.toggle('visibility');
    if (this.updateBtn == "update") {
      this.updateBtn = "back"
    } else {
      this.updateBtn = "update"
    }
  }

  updatePassword(passwordForm: {actualePassword: string, password: string, password2: string}): void {
    console.log(passwordForm.password)
    this.auth.updatePassword(this.profile!, passwordForm, )
    this.edit();
  }

}
