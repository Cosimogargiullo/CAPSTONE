package it.epicode.ecommerce.controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.Cart;
import it.epicode.ecommerce.models.Order;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.services.OrderService;
import it.epicode.ecommerce.services.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
//	GET ORDERS
	@GetMapping(path = "product/order/{userId}")
	public ResponseEntity<List<Order>> findAllOrder(@PathVariable(required = true) Long userId) {
		try {
			List<Order> find = orderService.findAll(userId);
			return new ResponseEntity<>(find, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
//	ADD PRODUCT TO Order
	@PostMapping(path = "/product/order/{userId}")
	public ResponseEntity<Order> addToOrder(@PathVariable(required = true) Long userId, @RequestBody Order order)
			throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		User user = userService.findById(userId).get();
		Order save = orderService.addToOrder(userId, order);
		try {
			sendEmail(order.getEmail(), user, order);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	public void sendEmail(String recipientEmail, User user, Order order) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		String mex= " <table class=\"table\">\n"
				+ "            <thead>\n"
				+ "              <tr>\n"
				+ "                <th scope=\"col\">Name</th>\n"
				+ "                <th scope=\"col\">Email</th>\n"
				+ "                <th scope=\"col\">Address</th>\n"
				+ "                <th scope=\"col\">Date</th>\n"
				+ "                <th scope=\"col\">Price</th>\n"
				+ "\n"
				+ "              </tr>\n"
				+ "            </thead>\n"
				+ "\n"
				+ "            <tbody id=\"prodottoCarrello\">\n"
				+ "\n"
				+ "              <tr *ngFor=\"let o of order\">\n"
				+ "                <td class=\"align-middle\">\n"
				+ "                  <p> "+ order.getName() +" "+ order.getSurname()+" </p>\n"
				+ "                </td>\n"
				+ "\n"
				+ "                <td class=\"align-middle\">\n"
				+ "                  <p> "+ order.getEmail()+" </p>\n"
				+ "                </td>\n"
				+ "\n"
				+ "                <td class=\"align-middle\">\n"
				+ "                  <p> "+order.getAddress() +", "+ order.getCity()+", "+ order.getState()+" , "+ order.getCap()+"</p>\n"
				+ "                </td>\n"
				+ "\n"
				+ "                <td class=\"align-middle\">\n"
				+ "                  <p> "+ order.getDate()+" </p>\n"
				+ "                </td>\n"
				+ "\n"
				+ "                <td class=\"align-middle\">\n"
				+ "                  <p>&euro; " + order.getTot()+ "</p>\n"
				+ "                </td>\n"
				+ "\n"
				+ "              </tr>\n"
				+ "\n"
				+ "            </tbody>\n"
				+ "          </table>";
		
		
		helper.setFrom("help@movo.it", "Movo Support");
		helper.setTo(recipientEmail);

		String subject = "Movo Order Confirmation";

		String content = mex;

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

}
