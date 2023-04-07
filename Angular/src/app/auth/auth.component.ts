import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductCategories } from '../models/product-category.inteface';
import { AuthService } from './auth.service';
import { HomepageComponent } from './homepage/homepage.component';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  link: string = "Login";

  constructor(private auth : AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  change(): void {
    switch (this.link) {
      case "Login": {
        this.router.navigate(["/guest/login"])
        this.link = "Homepage"
        break;
      }
      case "Homepage": {
        this.router.navigate(["/guest"])
        this.link = "Login"
        break;
      }
      default : {
        this.router.navigate(["/guest"])
      }
    }
  }

}
