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

  user: UserClass | undefined
  shop: Shop | undefined
  // profile: Profile | undefined
  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    // this.auth.getUserById(this.user?.id).subscribe(e => {
    //   this.profile = e
    // })
    this.auth.getShopById(this.user?.id).pipe(
      tap((res) => {
        this.shop = res
        console.log(res)
      }),
      catchError(error => {
        this.router.navigate(["/profile/shop"])
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    ).subscribe()
  }

}
