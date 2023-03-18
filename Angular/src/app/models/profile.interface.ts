export interface Profile {
  id: number
  username: string
  name: string
  surname: string
  email:string
  active: boolean
  password: string
  resetPasswordToken: string
  cart : {}[]
  favourite : {}[]
  orders : {}[]
  roles: string[]
}
