import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Product } from 'src/app/models/product.interface';
import { ProductCategories } from 'src/app/models/product-category.inteface';
import { Shop } from 'src/app/models/shop.interface';
import { UserClass } from 'src/app/models/user-class';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {

  productForm: Product | undefined
  categories: string[] = []
  categoriesObj: ProductCategories[] = []

  constructor(private auth: AuthService, private router: Router) { }

  user: UserClass | undefined
  shop: Shop | undefined
  product: Product | undefined

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.auth.getShopById(this.user?.id).subscribe(e => {
      this.shop = e
    })
    let prodId = window.location.href.split('/').pop();
    this.auth.getProductById(prodId).subscribe(p=>{
      this.product = p
      p.category.forEach(e=>{
        this.categories.push(e.name)
      })
    })
  }

  ngOnSubmit(productForm: Product) {

    this.categories.forEach((e, i) => {
      let o : ProductCategories = {
        name: e,
        id: 0
      }
      this.categoriesObj.push(o)
    })
    productForm.category! = this.categoriesObj

    console.log(productForm)
    document.getElementById('email')?.classList.add('d-none');
    document.getElementById('username')?.classList.add('d-none');
    document.getElementById('errGeneric')?.classList.add('d-none');
    this.auth.updateProduct(this.product?.id, productForm).pipe(
      tap((r) => {
        this.edit();
        setTimeout(() => {
          this.router.navigate(["/shop"])
        }, 1700);
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
  }

  edit(): void {
    document.getElementById("success")!.classList.remove('d-none');
  }

  back(): void {
    this.edit()
    let p = document.getElementById("password") as HTMLInputElement;
    p.value = ""
  }

  updateInfo(): void {
    document.getElementById("modale")!.classList.toggle('visibility');
  }

  categoryBtn() {
    document.getElementById("category-btn")!.classList.remove('d-none');

  }

  categoryBtn2() {
    document.getElementById("category-btn")!.classList.add('d-none');
  }

  addCategory(value: string): void {
    document.getElementById("errCat")!.classList.add('d-none');
    document.getElementById("errCatSize")!.classList.add('d-none');

    if (this.categories.includes(value)) {
      document.getElementById("errCat")!.classList.remove('d-none');
    } else
    if (this.categories.length == 3) {
      document.getElementById("errCatSize")!.classList.remove('d-none');
    } else {
      this.categories?.push(value)
    }
  }

  removeCategory(index: number): void {
    console.log(index)
    this.categories.splice(index, 1)
  }

}
