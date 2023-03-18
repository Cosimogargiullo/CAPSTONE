package it.epicode.ecommerce.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.models.Cart;
import it.epicode.ecommerce.repositories.CartRepository;

@Service
public class CartService  extends AbstractService<Cart, Long>{

	@Autowired
	CartRepository cartRepo;

	@Override
	protected JpaRepository<Cart, Long> getRepository() {
		return cartRepo;
	}

	@Override
	protected void beforeSave(Cart obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnInsert(Cart objectToInsert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnUpdate(Cart objectToUpdate) {
		// TODO Auto-generated method stub
		
	}
}
