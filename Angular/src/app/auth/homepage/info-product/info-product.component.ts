import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.interface';
import { ProductCategories } from 'src/app/models/product-category.inteface';
import { Shop } from 'src/app/models/shop.interface';
import { UserClass } from 'src/app/models/user-class';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-info-product',
  templateUrl: './info-product.component.html',
  styleUrls: ['./info-product.component.scss']
})
export class InfoProductComponent implements OnInit {

  logged: boolean = false;
  productForm: Product | undefined
  categories: string[] = []
  categoriesObj: ProductCategories[] = []

  constructor(private auth: AuthService, private router: Router) { }

  user: UserClass | undefined
  shop: Shop | undefined
  product: Product | undefined

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.user = JSON.parse(localStorage.getItem('user')!)
    if (this.user) {
      this.logged = true
    }
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

  add(id: any) {
    let qty = document.querySelector("#form1") as HTMLInputElement
    console.log(qty.value)
    this.auth.addUserCart(id, this.user?.id, qty.value).subscribe()
  }

}
