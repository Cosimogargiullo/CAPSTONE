package it.epicode.ecommerce.controllers;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import it.epicode.ecommerce.services.ProductService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

//	GET PRODUCTS
	@GetMapping(path = "/products")
	public ResponseEntity<List<Product>> findByAll() {
		try {
			List<Product> find = productService.findAll();
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	GET PRODUCTS BY CATEGORIES
	@GetMapping(path = "/products/{category}")
	public ResponseEntity<List<Product>> findByCategory(@PathVariable(required = true) String category) {
		List<Product> find = productService.findByCategory(category);
		return new ResponseEntity<>(find, HttpStatus.OK);
	}

//	GET PRODUCT BY ID
	@GetMapping(path = "/product/{id}")
	public ResponseEntity<Product> findById(@PathVariable(required = true) Long id) {
		try {
			Optional<Product> find = productService.findById(id);
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
//	GET PRODUCT BY NAME
	@GetMapping(path = "/product/keyword/{keyword}")
	public ResponseEntity<List<Product>> findBykeyword(@PathVariable(required = true) String keyword) {
		List<Product> find = productService.findByKeyword(keyword);
		return new ResponseEntity<>(find, HttpStatus.OK);
	}

//	CREATE PRODUCT
	@PostMapping(path = "/product/{id}")
	public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable(required = true) Long id)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		Product save = productService.createProduct(product, id);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
//	Populate PRODUCT
	@PostMapping(path = "/product/populate/{id}")
	public ResponseEntity<List<Product>> populateProduct(@RequestBody List<Product> product, @PathVariable(required = true) Long id)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		List<Product> result = new ArrayList<>();
		product.forEach(e-> {
			try {
			Product save = productService.createProduct(e, id);
			result.add(save);
			} catch (EcommerceExceptionNotValid nv) {
			} catch (EcommerceExceptionNotFound nf) {
			}
		});
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

//	UPDATE INFO DATA
	@PutMapping(path = "/product/update/{id}")
	public ResponseEntity<Product> updateInfo(@PathVariable Long id, @RequestBody Product product) {
		try {
			Product save = productService.updateInfo(id, product);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (EcommerceExceptionNotValid e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}

//	DELETE PRODUCT
	@DeleteMapping(path = "/product/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EcommerceExceptionNotFound {
		try {
			productService.delete(id);
			return new ResponseEntity<String>("Element deleted", HttpStatus.OK);
		} catch (EcommerceExceptionNotFound e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

}
