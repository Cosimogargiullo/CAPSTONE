package it.epicode.ecommerce.common.exceptions;

public class EcommerceExceptionNotFound extends EcommerceException {

	public EcommerceExceptionNotFound(String message) {
		super(message);
	}
	
	public EcommerceExceptionNotFound(String message, Throwable cause) {
		super(message);
	}

}
