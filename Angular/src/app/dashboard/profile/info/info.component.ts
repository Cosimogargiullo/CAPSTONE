import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';
import { FormControl, FormGroup } from '@angular/forms';
import { PasswordComponent } from '../password/password.component';
import { ProfileComponent } from '../profile.component';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit {

  isEditable: boolean = true;
  infoForm: Profile | undefined

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

  ngOnSubmit(infoForm: Profile) {
    this.edit();
    console.log(infoForm)
    this.auth.updateInfo(this.profile!, infoForm, infoForm.username, infoForm.password )
  }

  digit(): void {
    this.isEditable = !this.isEditable
    document.getElementById("submit")!.classList.toggle('visibility');
    if (this.updateBtn == "update") {
      this.updateBtn = "back"
    } else {
      this.updateBtn = "update"
    }
  }

  edit(): void {
    document.getElementById("modale")!.classList.toggle('visibility');
  }

  updateInfo(): void {
    document.getElementById("modale")!.classList.toggle('visibility');
  }

}
