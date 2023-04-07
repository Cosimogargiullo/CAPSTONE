import { ProductCategories } from "./product-category.inteface"

export interface Product {
  id: number
  title: string
  price: number
  description: string
  category: ProductCategories[]
  img: string
  rate: number
  rateCount: number
  favBtn? : boolean;
  count? : number;
}
