package it.epicode.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.epicode.ecommerce.models.Order;
import it.epicode.ecommerce.repositories.OrderRepository;

@Service
public class OrderService extends AbstractService<Order, Long>{

	@Autowired
	OrderRepository orderRepo;

	@Override
	protected JpaRepository<Order, Long> getRepository() {
		return orderRepo;
	}

	@Override
	protected void beforeSave(Order obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnInsert(Order objectToInsert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnUpdate(Order objectToUpdate) {
		// TODO Auto-generated method stub
		
	}
}
