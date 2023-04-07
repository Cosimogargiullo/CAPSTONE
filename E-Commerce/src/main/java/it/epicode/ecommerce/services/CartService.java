package it.epicode.ecommerce.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.models.Cart;
import it.epicode.ecommerce.models.Favourite;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.repositories.CartRepository;
import it.epicode.ecommerce.repositories.ProductRepository;
import it.epicode.ecommerce.repositories.UserRepository;

@Service
public class CartService  extends AbstractService<Cart, Long>{

	@Autowired
	CartRepository cartRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ProductRepository productRepo;

	@Override
	protected JpaRepository<Cart, Long> getRepository() {
		return cartRepo;
	}
	
	
	public int findProductsCount(Long userId, Long productId) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			List<Product> result = user.get().getCart().getProduct().stream().filter(e->e.getId()==productId).toList();
			return result.size();
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}
	
	public List<Product> findAll(Long id) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			List<Product> result = user.get().getCart().getProduct();
			return result;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}
	
	public Product addToCart(Long productId, Long userId, int qty) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			Optional<Cart> c = cartRepo.findById(user.get().getCart().getId());
			Optional<Product> p = productRepo.findById(productId);
			if (c.isPresent()) {
				if (qty > 0) {
					for (int i = 0; i < qty; i++) {
						c.get().addToCart(p.get());
						cartRepo.save(c.get());
					}
				}
				return p.get();
			} else {
				throw new EcommerceExceptionNotFound(
						"Cart id " + user.get().getFavourite().getId() + " not found");
			}
		} else {
			throw new EcommerceExceptionNotFound("User id " + userId + " not found");
		}
	}
	
	public Product removeFromCart(Long productId, Long userId) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			Optional<Cart> c = cartRepo.findById(user.get().getCart().getId());
			Optional<Product> p = productRepo.findById(productId);
			if (c.isPresent()) {
				c.get().remFromCart(p.get());
				cartRepo.save(c.get());
				return p.get();
			} else {
				throw new EcommerceExceptionNotFound(
						"Cart id " + user.get().getFavourite().getId() + " not found");
			}
		} else {
			throw new EcommerceExceptionNotFound("User id " + userId + " not found");
		}
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
