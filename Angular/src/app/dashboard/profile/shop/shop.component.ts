import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Profile } from 'src/app/models/profile.interface';
import { Shop } from 'src/app/models/shop.interface';
import { UserClass } from 'src/app/models/user-class';

@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.scss']
})
export class ShopComponent implements OnInit {


  isEditable: boolean = true;
  user: UserClass | undefined
  profile: Profile | undefined
  shopBtn: string = "create";
  shop: Shop | undefined

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.auth.getUserById(this.user?.id).subscribe(e => {
      this.profile = e
      this.auth.getShopById(e.id).pipe(
        tap((res) => {
          this.shop = res
          this.router.navigate(["/shop"])
          console.log("ok")
        }), catchError(error => {
          return of();
        }))
        .subscribe()
    })
  }
  back(): void {
    this.edit()
  }
  show(): void {
    this.isEditable = !this.isEditable
    document.getElementById("submit")!.classList.toggle('display');
    if (this.shopBtn == "create") {
      this.shopBtn = "back"
    } else {
      this.shopBtn = "create"
    }
  }
  edit(): void {
    document.getElementById("modale")!.classList.toggle('d-none');
  }

  ngOnSubmit(shopForm: Shop, password: string) {
    this.auth.login({ username: this.profile!.username, password: password }).pipe(
      tap((val) => {
        document.getElementById('i')?.classList.add('d-none');
        document.getElementById('name')?.classList.add('d-none');
        this.auth.createShop(this.profile?.id, shopForm).pipe(
          tap((res=> {
            console.log(res)
            document.getElementById('success')?.classList.remove('d-none');
            this.router.navigate(["/shop"])
          })),
          catchError(error=> {
            document.getElementById('name')?.classList.remove('d-none');
            document.getElementById("modale")!.classList.toggle('visibility');
            return of()
          })
        ).subscribe()
      }),
      catchError(error => {
        return of();
      })
    ).subscribe()
  }

}
