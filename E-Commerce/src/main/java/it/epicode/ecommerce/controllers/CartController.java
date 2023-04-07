package it.epicode.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.services.CartService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class CartController {
	
	@Autowired
	private CartService cartService;
	
//	GET cart PRODUCTS
	@GetMapping(path = "product/cart/count/{userId}/{prodId}")
	public ResponseEntity<Integer> findByAll(@PathVariable(required = true) Long userId, @PathVariable(required = true) Long prodId) {
		try {
			int find = cartService.findProductsCount(userId, prodId);
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
//	GET cart PRODUCTS
	@GetMapping(path = "product/cart/{id}")
	public ResponseEntity<List<Product>> findByAll(@PathVariable(required = true) Long id) {
		try {
			List<Product> find = cartService.findAll(id);
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
//	ADD PRODUCT TO CART
	@PutMapping(path = "/product/cart/add/{productId}/{userId}/{qty}")
	public ResponseEntity<Product> addToCart(@PathVariable(required = true) Long productId, @PathVariable(required = true) Long userId, @PathVariable(required = true) int qty)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		Product save = cartService.addToCart(productId, userId, qty);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
//	REMOVE PRODUCT TO CART
	@PutMapping(path = "/product/cart/remove/{productId}/{userId}")
	public ResponseEntity<Product> removeToCart(@PathVariable(required = true) Long productId, @PathVariable(required = true) Long userId)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		Product save = cartService.removeFromCart(productId, userId);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}


}
