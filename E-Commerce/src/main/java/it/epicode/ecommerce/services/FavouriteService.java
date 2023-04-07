package it.epicode.ecommerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.models.Favourite;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.repositories.FavouriteRepository;
import it.epicode.ecommerce.repositories.ProductRepository;
import it.epicode.ecommerce.repositories.UserRepository;

@Service
public class FavouriteService extends AbstractService<Favourite, Long> {

	@Autowired
	FavouriteRepository favouriteRepo;

	@Autowired
	UserRepository useriteRepo;

	@Autowired
	ProductRepository productRepo;

	@Override
	protected JpaRepository<Favourite, Long> getRepository() {
		return favouriteRepo;
	}

	public List<Product> findAll(Long id) throws EcommerceExceptionNotFound {
		Optional<User> user = useriteRepo.findById(id);
		if (user.isPresent()) {
			List<Product> result = user.get().getFavourite().getProduct();
			return result;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}

	public Product toggleToFavourite(Long productId, Long userId) throws EcommerceExceptionNotFound {
		Optional<User> user = useriteRepo.findById(userId);
		if (user.isPresent()) {
			Optional<Favourite> f = favouriteRepo.findById(user.get().getFavourite().getId());
			Optional<Product> p = productRepo.findById(productId);
			if (f.isPresent()) {
				f.get().toggleFavourite(p.get());
				favouriteRepo.save(f.get());
				return p.get();
			} else {
				throw new EcommerceExceptionNotFound(
						"Favourite id " + user.get().getFavourite().getId() + " not found");
			}
		} else {
			throw new EcommerceExceptionNotFound("User id " + userId + " not found");
		}
	}

	@Override
	protected void beforeSave(Favourite obj) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnInsert(Favourite objectToInsert) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnUpdate(Favourite objectToUpdate) {
		// TODO Auto-generated method stub

	}

}
