package it.epicode.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.Shop;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.repositories.ShopRepository;
import it.epicode.ecommerce.repositories.UserRepository;

@Service
public class ShopService extends AbstractService<Shop, Long> {

	@Autowired
	ShopRepository shopRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	protected JpaRepository<Shop, Long> getRepository() {
		return shopRepository;
	}
	
//	FIND BY ID
	public Optional<Shop> findById(Long id) throws EcommerceExceptionNotFound {
		Optional<Shop> user = shopRepository.findById(id);
		if (user.isPresent()) {
			return user;
		} else {
			throw new EcommerceExceptionNotFound("Could not find shop with id " + id);
		}
	}
	
	public Shop createShop(Shop shop, Long id) throws EcommerceExceptionNotValid {
		testUniqueId(shop, id, false);
		return this.insert(shop);
	}
	
	private void testUniqueId(Shop shop, Long id, boolean update) throws EcommerceExceptionNotValid {
		shop.setUser(userRepository.findById(id).get());
		Optional<Shop> shopById = shopRepository.findById(id);
		Optional<Shop> shopByName = shopRepository.findByShopName(shop.getShopName());

		if (shopById.isPresent() || shopByName.isPresent()) {

			if (update) {
				if (!shopById.get().getId().equals(shop.getId())) {
					throw new EcommerceExceptionNotValid("Shop with id: " + id + " already present");
				}
			} else {
				throw new EcommerceExceptionNotValid("Shop with id: " + id + " already present");
			}
		}
	}
	
	@Override
	protected void validateOnInsert(Shop objectToInsert) throws EcommerceExceptionNotValid {
	}

	@Override
	protected void validateOnUpdate(Shop objectToUpdate) throws EcommerceExceptionNotValid {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeSave(Shop shop) {
	}

}
