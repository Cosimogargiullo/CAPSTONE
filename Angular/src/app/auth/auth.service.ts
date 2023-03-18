import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap, catchError, of } from 'rxjs';
import { Profile } from '../models/profile.interface';
import { User } from '../models/user';
import { UserClass } from '../models/user-class';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user: UserClass | undefined

  private authBSubject = new BehaviorSubject<null | User>(null);

  userControl$ = this.authBSubject.asObservable();

  constructor(private http: HttpClient) { }

  login(user: {}) {
    return this.http.post<User>("http://localhost:8080/api/login", user).pipe(
      tap((val) => {
        console.log(val);
        this.authBSubject.next(val);
        localStorage.setItem('user', JSON.stringify(val));
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    );
  }

  createUser(token: string, type: string, id: number, username: string, email: string, roles: string[], expirationTime: Date) {
    this.user = new UserClass(token, type, id, username, email, roles, expirationTime);
  }

  getUsers() {
    return this.http.get<User[]>("http://localhost:8080/api/user");
  }
  getUserById(id: number | undefined) {
    return this.http.get<Profile>("http://localhost:8080/api/user/" + id);
  }

  updateInfo(profile: Profile, user: {}, username: any, password: any) {
    console.log({ username: "c.gargiullo", password: profile.password })
    return this.http.put<Profile>("http://localhost:8080/api/user/info/" + profile.id, user).pipe(
      tap((val) => {
        console.log(val);
        this.login({ username: username, password: password }).subscribe(data => {
          console.log(data);
          this.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
          localStorage.setItem('user', JSON.stringify(this.user))
        });
      })).subscribe();
  }

  updatePassword(profile: Profile, user: { actualePassword: string, password: string, password2: string }) {
    return this.http.put<Profile>("http://localhost:8080/api/user/password/" + profile.id, user).pipe(
      tap((val) => {
        console.log(val);
        this.login({ username: profile.username, password: user.password }).subscribe(data => {
          console.log(data);
          this.createUser(data.token, data.type, data.id, data.username, data.email, data.roles, data.expirationTime);
          localStorage.setItem('user', JSON.stringify(this.user))
        });
      })).subscribe();
  }

  singup(user: {}) {
    return this.http.post<Profile>("http://localhost:8080/api/singup/user/", user).subscribe(e => console.log(e))
  }

  forgotPassword(profile: {}) {
    return this.http.post<Profile>("http://localhost:8080/api/forgot/password", profile).subscribe(e=>{
      console.log(e)
    })
  }

  // resetToken(token: string) {
  //   return this.http.get<Profile>("http://localhost:8080/api/reset/password/" + token)
  // }
  getUserbyResetToken(token: string) {
    return this.http.get<Profile>("http://localhost:8080/api/reset/password/" + token);
  }

  resetPassword(id: number, password: {password: string}) {
    return this.http.put<Profile>("http://localhost:8080/api/user/password/" + id, password).subscribe()
  }
}
