package it.epicode.ecommerce.repositories;

import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
