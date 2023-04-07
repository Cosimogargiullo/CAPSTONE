package it.epicode.ecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.models.ProductCategory;
import it.epicode.ecommerce.services.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class ProductCategoryController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
//	GET PRODUCTS
	@GetMapping(path = "/categories")
	public ResponseEntity<List<ProductCategory>> findByAll() {
		try {
			List<ProductCategory> find = productCategoryService.findAll();
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
