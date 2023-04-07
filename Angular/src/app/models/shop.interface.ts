import { Product } from "./product.interface"

export interface Shop {
  id: number
  shopName: string
  img: string
  product: Product[]
}
