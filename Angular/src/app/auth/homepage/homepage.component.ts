import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.interface';
import { ProductCategories } from 'src/app/models/product-category.inteface';
import { Profile } from 'src/app/models/profile.interface';
import { UserClass } from 'src/app/models/user-class';
import { AuthService } from '../auth.service';
import { catchError, of, tap } from 'rxjs';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  logged: boolean = false;
  user: UserClass | undefined
  profile: Profile | undefined

  favToggle : boolean = true;
  products: Product[] | undefined
  categories: ProductCategories[] | undefined
  linkCategory: string | undefined;
  favourites: Product[] | undefined;

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)

    if (this.user) {
      this.logged = true
    }

    // GET PRODUCT CATEGORIES
    this.auth.getAllProductCategories().pipe(
      tap((val) => {
        console.log(val);
        this.categories = val
      }),
      catchError(error => {
        return of();
      })
    ).subscribe()

    this.updateFavourite()

    this.auth.getAllProducts()
    .pipe(
      tap((val) => {
        console.log(val);
        this.products = val
      }),
      catchError(error => {
        return of();
      })
    ).subscribe()

    this.populatProducts("guest")
  }

  updateFavourite(): void {
    this.auth.getUserFavourites(this.user?.id).pipe(
      tap((val) => {
        this.favourites = val
      }),
      catchError(error => {
        return of();
      })
    ).subscribe()
  }

  populatProducts(criteria: string): void {
    if (criteria != "guest") {
      this.auth.getProductByCategory(criteria).subscribe(e => {
        this.products = e
      })
    }
    this.auth.getAllProducts().pipe(
      tap((val) => {
        this.products = val
        this.products.forEach(p=>{
          this.favourites!.forEach(f=>{
          if (p.id == f.id) {
            p.favBtn = true
          }
          })
        })
      }),
      catchError(error => {
        return of();
      })
    ).subscribe()
  }

  category(category: any): void {
    this.populatProducts(category)
  }

  orderBy(criteria: any): void {
    switch (criteria) {
      case "asc": {
        this.products?.sort((a, b) => (a.price > b.price ? 1 : -1));
        break
      }
      case "des": {
        this.products?.sort((a, b) => (a.price < b.price ? 1 : -1));
        break
      }
    }
  }

  search(search: string): void {
    this.auth.getProductByKeyword(search).subscribe(e => {
      this.products = e
    })
    console.log(search)
  }

  favouriteSection(): void {
    if (this.logged) {
      if (this.favToggle) {
        this.auth.getUserFavourites(this.user?.id).subscribe(e => {
          this.products = e
        })
        this.favToggle = !this.favToggle;
      } else {
        this.populatProducts("guest")
        this.favToggle = !this.favToggle;
      }
    }
  }

  addFavourite(pId: any): void {
    document.getElementById("add"+pId)?.classList.toggle("fav-button")
    this.auth.addUserFavourite(pId, this.user?.id).subscribe()
  }

  delFavourite(pId: any): void {
    this.auth.addUserFavourite(pId, this.user?.id).subscribe(e=>{
      this.auth.getUserFavourites(this.user?.id).subscribe(e => {
        this.products = e
      })
    })
  }

}
