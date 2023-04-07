import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { Order } from 'src/app/models/order.interface';
import { Product } from 'src/app/models/product.interface';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {


  order: Order[] | undefined;
  user: User | undefined;
  tot: number = 0

  constructor(private auth: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('user')!)
    this.auth.getAllOrders(this.user?.id).subscribe(e => {
      this.order = e
    })
  }

}
