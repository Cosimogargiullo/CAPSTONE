import { Order } from "./order.interface"
import { Shop } from "./shop.interface"

export interface Profile {
  id: number
  username: string
  name: string
  surname: string
  email:string
  active: boolean
  password: string
  resetPasswordToken: string
  shop : Shop
  cart : {}[]
  favourite : {}[]
  orders : Order[]
  roles: string[]
}
