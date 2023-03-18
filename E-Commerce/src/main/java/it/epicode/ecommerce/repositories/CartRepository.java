package it.epicode.ecommerce.repositories;

import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

}
