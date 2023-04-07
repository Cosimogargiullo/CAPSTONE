import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';

@Component({
  selector: 'app-security-component',
  templateUrl: './security-component.component.html',
  styleUrls: ['./security-component.component.scss']
})
export class SecurityComponentComponent implements OnInit {

  isEditable: boolean = true;
  user: UserClass | undefined
  profile: Profile | undefined
  deleteBtn: string = "delete";

  constructor(private auth : AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.auth.getUserById(this.user?.id).subscribe(e => {
      this.profile = e
    })
  }
  back(): void {
    this.edit()
  }
  show(): void {
    this.isEditable = !this.isEditable
    document.getElementById("delete")!.classList.toggle('display');
    if (this.deleteBtn == "delete") {
      this.deleteBtn = "back"
    } else {
      this.deleteBtn = "delete"
    }
  }
  edit(): void {
    document.getElementById("modale")!.classList.toggle('visibility');
  }

  ngOnSubmit(passwordForm: {password: string}) {
    this.auth.login({ username: this.profile!.username, password: passwordForm.password }).pipe(
      tap((val) => {
        document.getElementById('i')?.classList.add('d-none');
        this.auth.deleteUser(this.profile!)
      }),
      catchError(error => {
        console.log("not ok");
        return of();
      })
    ).subscribe()
  }

}
