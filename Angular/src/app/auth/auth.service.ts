import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, tap, catchError, of } from 'rxjs';
import { Product } from '../models/product.interface';
import { ProductCategories } from '../models/product-category.inteface';
import { Profile } from '../models/profile.interface';
import { Shop } from '../models/shop.interface';
import { User } from '../models/user';
import { UserClass } from '../models/user-class';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Order } from '../models/order.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user: UserClass | undefined

  private authBSubject = new BehaviorSubject<null | User>(null);

  userControl$ = this.authBSubject.asObservable();

  jwtHelper = new JwtHelperService();

  timeoutLogout: any;

  constructor(private http: HttpClient, private router: Router) {
    this.restore()
  }

  restore() {
    const user = localStorage.getItem('user');

    if (!user) {
      return;
    }
    const userData: User = JSON.parse(user);
    console.log(user);
    if (this.jwtHelper.isTokenExpired(userData.token)) {
      return;
    }

    this.authBSubject.next(userData);
    this.autoLogout(userData);
  }

  autoLogout(data: User) {
    const expDate = this.jwtHelper.getTokenExpirationDate(
      data.token
    ) as Date;
    const operationEx = expDate.getTime() - new Date().getTime();
    this.timeoutLogout = setTimeout(() => {
      this.logout();
    }, operationEx);
  }

  // LOGIN FUNCTION
  login(user: {}) {
    return this.http.post<User>("http://localhost:8080/api/login", user).pipe(
      tap((val) => {
        console.log(val);
        this.authBSubject.next(val);
        localStorage.setItem('user', JSON.stringify(val));
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    );
  }

  logout() {
    this.authBSubject.next(null);
    localStorage.removeItem("user");
    this.router.navigate(['/guest']);
    if (this.timeoutLogout) {
      clearTimeout(this.timeoutLogout);
    }
    // localStorage.removeItem("user");
    // this.router.navigate(['/guest']);
  }

  // CREATE USER TO STORE IN LOCAL STORAGE
  createUser(token: string, type: string, id: number, username: string, email: string, roles: string[], expirationTime: Date) {
    this.user = new UserClass(token, type, id, username, email, roles, expirationTime);
  }

  // SIGN-UP FUNCTION
  singup(user: {}) {
    return this.http.post<Profile>("http://localhost:8080/api/singup/user/", user).pipe(
      tap((val) => {
        document.getElementById("modal")!.classList.remove('d-none');
        setTimeout(() => {
          this.router.navigate(["/guest/login"])
        }, 1700);
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    );
  }

  // FORGOT PASSWORD SENDS EMAIL OF RESET PASSWORD
  forgotPassword(profile: {}) {
    return this.http.post("http://localhost:8080/api/forgot/password", profile, { responseType: 'text' }).pipe(
      tap((val) => {
        document.getElementById('i')?.classList.add('d-none');
        console.log(val);
        document.getElementById("success")!.classList.remove('d-none');
      }),
      catchError(error => {
        document.getElementById('i')?.classList.remove('d-none');
        return of();
      })
    );
  }

  // GET USER BY RESET TOKEN
  getUserbyResetToken(token: string) {
    return this.http.get<Profile>("http://localhost:8080/api/reset/password/" + token);
  }

  // RESET PASSWORD FUNCTION
  resetPassword(id: number, password: { password: string }) {
    return this.http.put<Profile>("http://localhost:8080/api/user/password/" + id, password).subscribe()
  }

  // GET USER BY ID FUNCTION
  getUserById(id: number | undefined) {
    return this.http.get<Profile>("http://localhost:8080/api/user/" + id);
  }

  // UPDATE INFO FUNCTION
  updateInfo(profile: Profile, user: Profile) {
    return this.http.put<Profile>("http://localhost:8080/api/user/info/" + profile.id, user)
  }

  // UPDATE PASSWORD FUNCTION
  updatePassword(profile: Profile, user: { actualPassword: string, password: string, password2: string }) {
    return this.http.put<Profile>("http://localhost:8080/api/user/password/" + profile.id, user)
  }

  // DELETE USER FUNCTION
  deleteUser(profile: Profile) {
    return this.http.delete("http://localhost:8080/api/user/" + profile.id, { responseType: 'text' }).pipe(
      tap((val) => {
        document.getElementById('success')?.classList.remove('d-none');
        document.getElementById('success-spin')?.classList.remove('d-none');
        setTimeout(() => {
          this.router.navigate(["/guest/login"])
        }, 2000);
      })
    ).subscribe()
  }


  getUsers() {
    return this.http.get<User[]>("http://localhost:8080/api/user");
  }

  // SHOP -----------------------------------------------

  // CREATE Shop FUNCTION
  createShop(id: number | undefined, shopInfo: Shop) {
    return this.http.post<Shop>("http://localhost:8080/api/shop/" + id, shopInfo);
  }
  // GET SHOP BY ID FUNCTION
  getShopById(id: number | undefined) {
    return this.http.get<Shop>("http://localhost:8080/api/shop/" + id);
  }

  // PRODUCT -----------------------------------------------

  // GET ALL PRODUCTS
  getAllProducts() {
    return this.http.get<Product[]>("http://localhost:8080/api/products");
  }
  // GET PRODUCT BY ID FUNCTION
  getProductById(id: any) {
    return this.http.get<Product>("http://localhost:8080/api/product/" + id);
  }
  // GET PRODUCT BY CATEGORY FUNCTION
  getProductByCategory(category: any) {
    return this.http.get<Product[]>("http://localhost:8080/api/products/" + category);
  }
  // GET PRODUCT BY KEYWORD FUNCTION
  getProductByKeyword(keyword: any) {
    return this.http.get<Product[]>("http://localhost:8080/api/product/keyword/"+keyword);
  }
  // CREATE PRODUCT FUNCTION
  createProduct(id: number | undefined, prodInfo: Product) {
    return this.http.post<Product>("http://localhost:8080/api/product/" + id, prodInfo);
  }

  // UPDATE INFO FUNCTION
  updateProduct(id: number | undefined, prodInfo: Product) {
    return this.http.put<Profile>("http://localhost:8080/api/product/update/" + id, prodInfo)
  }

  // DELETE PRODUCT FUNCTION
  deleteProduct(id: number) {
    return this.http.delete("http://localhost:8080/api/product/" + id, { responseType: 'text' }).pipe(
      tap((val) => {
        document.getElementById('success')?.classList.remove('d-none');
        document.getElementById('success-spin')?.classList.remove('d-none');
        setTimeout(() => {
          window.location.reload()
        }, 2000);
      })
    ).subscribe()

  }

  // PRODUCT CATEGORY -----------------------------------------------

  getAllProductCategories() {
    return this.http.get<ProductCategories[]>("http://localhost:8080/api/categories");
  }

  // FAVOURITES -----------------------------------------------

  getUserFavourites(id: any) {
    return this.http.get<Product[]>("http://localhost:8080/api/product/favourite/" + id);
  }

  addUserFavourite(id: any, userId: any) {
    return this.http.put<Product>(`http://localhost:8080/api/product/favourite/${id}/${userId}`, id);
  }

  // CART -----------------------------------------------

  getUserCart(id: any) {
    return this.http.get<Product[]>("http://localhost:8080/api/product/cart/" + id);
  }

  addUserCart(id: any, userId: any, qty: any) {
    return this.http.put<Product>(`http://localhost:8080/api/product/cart/add/${id}/${userId}/${qty}`, id);
  }
  removeUserCart(id: any, userId: any) {
    return this.http.put<Product>(`http://localhost:8080/api/product/cart/remove/${id}/${userId}`, id);
  }

  productCountCart(userId: any, id: any) {
    return this.http.get<number>(`http://localhost:8080/api/product/cart/count/${userId}/${id}/`);
  }

  // ORDERS -----------------------------------------------

  createOrder(userId: any, orderInfo: Order) {
    return this.http.post<Product>("http://localhost:8080/api/product/order/" + userId, orderInfo);
  }

  getAllOrders(userId: any) {
    return this.http.get<Order[]>("http://localhost:8080/api/product/order/" + userId);
  }
}
