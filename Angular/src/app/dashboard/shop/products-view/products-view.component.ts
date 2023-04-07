import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { Product } from 'src/app/models/product.interface';
import { Shop } from 'src/app/models/shop.interface';
import { UserClass } from 'src/app/models/user-class';

@Component({
  selector: 'app-products-view',
  templateUrl: './products-view.component.html',
  styleUrls: ['./products-view.component.scss']
})
export class ProductsViewComponent implements OnInit {

  user: UserClass | undefined
  products: Product[] | undefined


  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    // this.auth.getUserById(this.user?.id).subscribe(e => {
    //   this.profile = e
    // })
    this.auth.getShopById(this.user?.id).subscribe(e => {
      this.products = e.product
        console.log(e.product)
    })
  }

  deleteProduct(id : number): void {
    this.auth.deleteProduct(id)
  }

}
