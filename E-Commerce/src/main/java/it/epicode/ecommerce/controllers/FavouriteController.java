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
import it.epicode.ecommerce.services.FavouriteService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class FavouriteController {
	
	@Autowired
	private FavouriteService favouriteService;
	
//	GET FAVOURITE PRODUCTS
	@GetMapping(path = "product/favourite/{id}")
	public ResponseEntity<List<Product>> findByAll(@PathVariable(required = true) Long id) {
		try {
			List<Product> find = favouriteService.findAll(id);
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
//	ADD PRODUCT TO FAVOURITE
	@PutMapping(path = "/product/favourite/{productId}/{userId}")
	public ResponseEntity<Product> toggleFavourite(@PathVariable(required = true) Long productId, @PathVariable(required = true) Long userId)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		Product save = favouriteService.toggleToFavourite(productId, userId);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

}
