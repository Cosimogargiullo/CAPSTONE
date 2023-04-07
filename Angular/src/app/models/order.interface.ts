import { Profile } from "./profile.interface"

export interface Order {
	id : number
	name : string
  surname  : string
	email  : string
	address  : string
	city  : string
	state  : string
	date : Date
	user : Profile
  tot: number
  cap : number
}
