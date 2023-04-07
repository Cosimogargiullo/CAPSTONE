import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { Product } from 'src/app/models/product.interface';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cart: Product[] | undefined;
  user: User | undefined;
  tot: number = 0

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)

    this.auth.getUserCart(this.user?.id).subscribe(e => {
      e.sort()
      for (let i =0; i < e.length; i++) {
        e[i].count = 0
        let delIndex = []
        for (let j = i+1 ; j < e.length; j++) {
          if (e[i].id == e[j].id) {
            delIndex.push(i)
          }
        }
        for (let i =0; i < delIndex.length; i++) {
          e.splice(delIndex[i], 1)
        }
      }
      this.cart = e
      this.cart.forEach(p=>{
        this.auth.productCountCart(this.user?.id, p.id).subscribe(c=>{
          p.count = c
          this.tot += p.price*p.count
        })
      })
    })
  }

  remove(pId: any) {
    this.auth.removeUserCart(pId, this.user?.id).subscribe()
    window.location.reload()
  }

  add(pId: any) {
    this.auth.addUserCart(pId, this.user?.id, 1).subscribe()
    window.location.reload()
  }



}
