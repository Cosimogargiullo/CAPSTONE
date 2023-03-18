package it.epicode.ecommerce.services;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String string) {
		System.out.println(string);
	}

}
