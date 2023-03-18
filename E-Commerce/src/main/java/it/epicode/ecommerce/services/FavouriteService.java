package it.epicode.ecommerce.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.epicode.ecommerce.models.Favourite;
import it.epicode.ecommerce.repositories.FavouriteRepository;

@Service
public class FavouriteService extends AbstractService<Favourite, Long>{

	@Autowired
	FavouriteRepository favouriteRepo;
	
	@Override
	protected JpaRepository<Favourite, Long> getRepository() {
		return favouriteRepo;
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
