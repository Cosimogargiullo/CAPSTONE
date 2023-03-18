package it.epicode.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.repositories.ProductRepository;

@Service
public class ProductService  extends AbstractService<Product, Long>{

	@Autowired
	ProductRepository productRepo;

	@Override
	protected JpaRepository<Product, Long> getRepository() {
		return productRepo;
	}

	@Override
	protected void beforeSave(Product obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnInsert(Product objectToInsert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnUpdate(Product objectToUpdate) {
		// TODO Auto-generated method stub
		
	}
	
}
