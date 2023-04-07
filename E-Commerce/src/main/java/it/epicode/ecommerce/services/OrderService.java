package it.epicode.ecommerce.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.models.Order;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.repositories.OrderRepository;
import it.epicode.ecommerce.repositories.ProductRepository;
import it.epicode.ecommerce.repositories.UserRepository;

@Service
public class OrderService extends AbstractService<Order, Long> {

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ProductRepository productRepo;

	@Override
	protected JpaRepository<Order, Long> getRepository() {
		return orderRepo;
	}

	public List<Order> findAll(Long id) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			List<Order> result = user.get().getOrder();
			return result;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}

	public Order addToOrder(Long userId, Order o) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepo.findById(userId);
		if (user.isPresent()) {
			double total = 0;
			List<Product> cartProd = user.get().getCart().getProduct();
			for(int i = 0; i < cartProd.size(); i++) {
			    total += cartProd.get(i).getPrice();
			    }
			
			Order order = new Order().builder()
					.name(o.getName())
					.surname(o.getSurname())
					.email(o.getEmail())
					.address(o.getAddress())
					.city(o.getCity())
					.date(Timestamp.valueOf(LocalDateTime.now()))
					.state(o.getState()).cap(o.getCap())
					.tot(total)
					.user(user.get()).build();
			Order save = orderRepo.save(order);
			user.get().getCart().setProduct(new ArrayList<Product>());
			userRepo.save(user.get());
			return save;
		} else {
			throw new EcommerceExceptionNotFound("User id " + userId + " not found");
		}
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
