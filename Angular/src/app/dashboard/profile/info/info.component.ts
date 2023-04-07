import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';
import { FormControl, FormGroup } from '@angular/forms';
import { PasswordComponent } from '../password/password.component';
import { ProfileComponent } from '../profile.component';
import { catchError, of, tap } from 'rxjs';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.scss']
})
export class InfoComponent implements OnInit {

  isEditable: boolean = true;
  infoForm: Profile | undefined

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

  ngOnSubmit(infoForm: Profile) {
    document.getElementById('email')?.classList.add('d-none');
    document.getElementById('username')?.classList.add('d-none');
    document.getElementById('errGeneric')?.classList.add('d-none');
    this.auth.login({ username: this.profile!.username, password: infoForm.password }).pipe(
      tap((val) => {
        this.auth.updateInfo(this.profile!, infoForm).pipe(
          tap((r) => {
            this.auth.login({ username: infoForm.username, password: infoForm.password }).subscribe(data => {
              console.log(data);
              this.auth.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
              localStorage.setItem('user', JSON.stringify(this.auth.user))
              this.edit();
              this.router.navigate(["/"])
            })
          }),
          catchError(error => {
            if (error instanceof HttpErrorResponse) {
              if (error.error instanceof ErrorEvent) {
                console.error("Event Error");
              } else {
                console.log(`error status : ${error.status} ${error.statusText}`);
                switch (error.status) {
                  case 409:      //EMAIL
                    document.getElementById('email')?.classList.remove('d-none');
                    this.back();
                    console.log("409")
                    break;
                  case 405:     //USERNAME
                    document.getElementById('username')?.classList.remove('d-none');
                    this.back();
                    console.log("405")
                    break;
                  case 404:     //USER NOT FOUND
                    document.getElementById('errGeneric')?.classList.remove('d-none');
                    this.back();
                    console.log("404")
                    break;
                }
              }
            } else {
              console.error("Generic Error! some thing else happened");
            }
            return of();
          })
        ).subscribe()
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    ).subscribe()

    // console.log(infoForm)
    // this.auth.login({ username: this.profile!.username, password: infoForm.password }).pipe(
    //   tap((val) => {
    //     this.auth.updateInfo(this.profile!, infoForm).subscribe(r => {
    //       if (r.username == infoForm.username) {
    //         this.auth.login({ username: infoForm.username, password: infoForm.password }).subscribe(data => {
    //           console.log(data);
    //           this.auth.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
    //           localStorage.setItem('user', JSON.stringify(this.auth.user))
    //           this.edit();
    //           this.router.navigate(["/"])
    //         });
    //       } else {
    //         console.log('errore')
    //       }
    //     })
    //   }),
    //   catchError(error => {
    //     document.getElementById('i')?.classList.remove('d-none');
    //     return of();
    //   })
    // ).subscribe()

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
  back(): void {
    this.edit()
    let p = document.getElementById("password") as HTMLInputElement;
    p.value = ""
  }

  updateInfo(): void {
    document.getElementById("modale")!.classList.toggle('visibility');
  }

}
