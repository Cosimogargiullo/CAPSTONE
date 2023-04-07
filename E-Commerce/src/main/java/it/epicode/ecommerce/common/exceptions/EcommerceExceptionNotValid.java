package it.epicode.ecommerce.common.exceptions;

public class EcommerceExceptionNotValid extends EcommerceException {

	public EcommerceExceptionNotValid(String message) {
		super(message);
	}
	
	public EcommerceExceptionNotValid(String message, Throwable cause) {
		super(message);
	}

}
