package it.epicode.ecommerce.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.services.FavouriteService;
import it.epicode.ecommerce.services.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	FavouriteService favouriteService;

	@Autowired
	private JavaMailSender mailSender;

//	GET ALL USERS
	@GetMapping(path = "/users")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> findAll = userService.findAll(pageable);
		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	GET USER BY ID
	@GetMapping(path = "/user/{id}")
	public ResponseEntity<User> findById(@PathVariable(required = true) Long id) {
		try {
			Optional<User> find = userService.findById(id);
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	SING UP FUNCTION
	@PostMapping(path = "/singup/user")
	public ResponseEntity<User> singupUser(@RequestBody User user) throws EcommerceExceptionNotValid {
		User create = new User();
		create.setName(user.getName());
		create.setSurname(user.getName());
		create.setEmail(user.getEmail());
		create.setUsername(user.getUsername());
		create.setPassword(user.getPassword());
		User save = userService.insert(create);
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

//	FORGOT PASSWORD SENDS RESET PASSWORD EMAIL
	@PostMapping("/forgot/password")
	public ResponseEntity<String> processForgotPassword(HttpServletRequest request, @RequestBody User user,
			Model model) {
		String email = user.getEmail();
		String token = RandomString.make(30);

		try {
			userService.updateResetPasswordToken(token, email);
			String link = request.getRequestURL().toString();
			link.replace(request.getServletPath(), "");
			String resetPasswordLink = "http://localhost:4200/guest/reset/password/" + token;
			try {
				sendEmail(email, resetPasswordLink);
			} catch (UnsupportedEncodingException e) {
				model.addAttribute("error", "Error while sending email");
				e.printStackTrace();
			} catch (MessagingException e) {
				model.addAttribute("error", "Error while sending email");
				e.printStackTrace();
			}
			model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
		} catch (EcommerceExceptionNotFound ex) {
			model.addAttribute("error", ex.getMessage());
		}

		return new ResponseEntity<>("Reset-link sent", HttpStatus.OK);
	}

	public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("help@movo.it", "Movo Support");
		helper.setTo(recipientEmail);

		String subject = "Here's the link to reset your password";

		String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
				+ "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + link
				+ "\">Change my password</a></p>" + "<br>" + "<p>Ignore this email if you do remember your password, "
				+ "or you have not made the request.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}

//	RESET PASSWORD
	@PutMapping(path = "/user/password/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
		try {
			User save = userService.updatePassword(id, user);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound ex) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

//	UPDATE INFO DATA
	@PutMapping(path = "/user/info/{id}")
	public ResponseEntity<User> updateInfo(@PathVariable Long id, @RequestBody User user) {
		try {
			User save = userService.updateInfo(id, user);
			return new ResponseEntity<>(save, HttpStatus.OK);
		} catch (EcommerceExceptionNotFound e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (EcommerceExceptionNotValid e) {
			if (e.getMessage().equals("EMAIL")) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
			}
		}
	}

//	DELETE FUNCTION
	@DeleteMapping(path = "/user/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws EcommerceExceptionNotFound {
		try {
			userService.delete(id);
			return new ResponseEntity<String>("Element deleted", HttpStatus.OK);
		} catch (EcommerceExceptionNotFound e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(path = "/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		User save = userService.update(id, user);
		return new ResponseEntity<>(save, HttpStatus.OK);
	} 

	@GetMapping("/reset/password/{token}")
	public User showResetPasswordForm(@PathVariable String token, Model model) {
		Optional<User> user = userService.getByResetPasswordToken(token);
		if (user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}

//	@PostMapping(path = "/user")
//	@PreAuthorize("hasRole('User')")
//	public ResponseEntity<User> save(@RequestBody User user) {
//		User save = userService.save(user);
//		return new ResponseEntity<>(save, HttpStatus.OK);
//	}

}
