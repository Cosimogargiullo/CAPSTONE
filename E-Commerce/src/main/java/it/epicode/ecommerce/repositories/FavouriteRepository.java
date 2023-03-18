package it.epicode.ecommerce.repositories;

import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long>{

}
