package it.epicode.ecommerce.repositories;

import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
