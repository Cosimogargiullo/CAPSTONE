import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
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
  passwordForm: { actualPassword: string, password: string, password2: string } | undefined

  constructor(private auth: AuthService, private router: Router) { }

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
      this.back()
    }
  }

  back(): void {
    document.getElementById('i')?.classList.add('d-none');
    let ap = document.getElementById("actualPassword") as HTMLInputElement;
    ap.value = ""
    let p = document.getElementById("password") as HTMLInputElement;
    p.value = ""
    let p2 = document.getElementById("password2") as HTMLInputElement;
    p2.value = ""
    document.getElementById('success')?.classList.add('d-none')
  }

  updatePassword(passwordForm: { actualPassword: string, password: string, password2: string }): void {
    this.auth.login({ username: this.profile!.username, password: passwordForm.actualPassword }).pipe(
      tap((val) => {
        this.auth.updatePassword(this.profile!, passwordForm).pipe(
          tap((r) => {
            this.auth.login({ username: this.profile!.username, password: passwordForm.password }).subscribe(data => {
              console.log(data);
              this.auth.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
              localStorage.setItem('user', JSON.stringify(this.auth.user))
              this.edit();
              document.getElementById('i')?.classList.add('d-none')
              document.getElementById('success')?.classList.remove('d-none')
              setTimeout(()=>{
                this.back()
            }, 2000);
            })
          }),
          catchError(error => {
            document.getElementById('errGeneric')?.classList.remove('d-none');
            return of();
          })
        ).subscribe()
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    ).subscribe()
  }

}
