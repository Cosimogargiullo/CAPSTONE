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
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.services.UserNotFoundException;
import it.epicode.ecommerce.services.UserService;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping(path = "/user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<User>> findAll(Pageable pageable) {
		Page<User> findAll = userService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/user2")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<User>> findAll2(Pageable pageable) {
		Page<User> findAll = userService.findAll(pageable);

		if (findAll.hasContent()) {
			return new ResponseEntity<>(findAll, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> findById(@PathVariable(required = true) Long id) {
		Optional<User> find = userService.findById(id);
		if (find.isPresent()) {
			return new ResponseEntity<>(find.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping(path = "/user")
	@PreAuthorize("hasRole('User')")
	public ResponseEntity<User> save(@RequestBody User user) {
		User save = userService.save(user);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PostMapping(path = "/singup/user")
//	@PreAuthorize("hasRole('User')")
	public ResponseEntity<User> singupUser(@RequestBody User user) {
		User save = userService.singupUser(user);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		User save = userService.update(id, user);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/user/info/{id}")
	public ResponseEntity<User> updateInfo(@PathVariable Long id, @RequestBody User user) {
		User save = userService.updateInfo(id, user);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@PutMapping(path = "/user/password/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody User user) {
		User save = userService.updatePassword(id, user);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@DeleteMapping(path = "/user/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {
		userService.delete(id);
		return new ResponseEntity<>("Element deleted", HttpStatus.OK);
	}
	
	@PostMapping("/forgot/password")
	public ResponseEntity<String> processForgotPassword(HttpServletRequest request,@RequestBody User user, Model model) {
	    String email = user.getEmail();
	    String token = RandomString.make(30);
	     
	    try {
	        userService.updateResetPasswordToken(token, email);
	        String link = request.getRequestURL().toString();
	        link.replace(request.getServletPath(), "");
	        String resetPasswordLink =  "http://localhost:4200/reset/password/" + token;
	        try {
				sendEmail(email, resetPasswordLink);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
	         
	    } catch (UserNotFoundException ex) {
	        model.addAttribute("error", ex.getMessage());
	    } 
//	    catch (UnsupportedEncodingException | MessagingException e) {
//	        model.addAttribute("error", "Error while sending email");
//	    }    
	    return new ResponseEntity<>("reset-link sent", HttpStatus.OK);
	}
	
	public void sendEmail(String recipientEmail, String link)
	        throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = mailSender.createMimeMessage();              
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    helper.setFrom("contact@shopme.com", "Shopme Support");
	    helper.setTo(recipientEmail);
	     
	    String subject = "Here's the link to reset your password";
	     
	    String content = "<p>Hello,</p>"
	            + "<p>You have requested to reset your password.</p>"
	            + "<p>Click the link below to change your password:</p>"
	            + "<p><a href=\"" + link + "\">Change my password</a></p>"
	            + "<br>"
	            + "<p>Ignore this email if you do remember your password, "
	            + "or you have not made the request.</p>";
	     
	    helper.setSubject(subject);
	     
	    helper.setText(content, true);
	     
	    mailSender.send(message);
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

}
