import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { Order } from 'src/app/models/order.interface';
import { Product } from 'src/app/models/product.interface';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {

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

  onSubmit(checkData: Order): void {
    checkData.tot = this.tot+ 10
    console.log(checkData)
    this.auth.createOrder(this.user?.id, checkData).pipe(
      tap((val) => {
        console.log(val);
        document.getElementById("modale")?.classList.remove("d-none")
        setTimeout(() => {
          this.router.navigate(['/orders']);
        }, 2000);
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    ).subscribe()
  }

}
