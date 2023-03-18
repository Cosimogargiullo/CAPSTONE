package it.epicode.ecommerce.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.models.Role;
import it.epicode.ecommerce.models.User;
import it.epicode.ecommerce.repositories.RoleRepository;
import it.epicode.ecommerce.repositories.UserRepository;

@Service
public class UserService extends AbstractService<User, Long> {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder pe;

	@Autowired
	RoleRepository roleRepository;

	@Override
	protected JpaRepository<User, Long> getRepository() {
		return userRepository;
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Page<User> findByNome(String nome, Pageable pageable) {
		return userRepository.findByName(nome, pageable);
	}

	public Optional<User> findByUsername(String nome) {
		return userRepository.findByUsername(nome);
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public User update(Long id, User user) {
		Optional<User> userResult = userRepository.findById(id);

		if (userResult.isPresent()) {
			User userUpdate = userResult.get();
//			userUpdate.setNome(user.getNome());
			userRepository.save(user);
			return userUpdate;
		} else {
			throw new Error("Elemento non aggiornato");
		}
	}

	public User updateInfo(Long id, User user) {
		Optional<User> userResult = userRepository.findById(id);

		if (userResult.isPresent()) {
			User userUpdate = userResult.get();
			userUpdate.setName(user.getName());
			userUpdate.setSurname(user.getSurname());
			userUpdate.setUsername(user.getUsername());
			userUpdate.setEmail(user.getEmail());
			userRepository.save(userUpdate);
			return userUpdate;
		} else {
			throw new Error("Elemento non aggiornato");
		}
	}

	public User updatePassword(Long id, User user) {
		Optional<User> userResult = userRepository.findById(id);

		if (userResult.isPresent()) {
			User userUpdate = userResult.get();
			userUpdate.setPassword(pe.encode(user.getPassword()));
			userRepository.save(userUpdate);
			return userUpdate;
		} else {
			throw new Error("Elemento non aggiornato");
		}
	}

	public User singupUser(User user) {
		Optional<User> userResult = userRepository.findByUsername(user.getUsername());

		if (!(userResult.isPresent())) {
			User userSave = new User();
			userSave.setName(user.getName());
			userSave.setSurname(user.getSurname());
			userSave.setUsername(user.getUsername());
			userSave.setEmail(user.getEmail());
			userSave.setPassword(pe.encode(user.getPassword()));
			userSave.setRoles(new HashSet<Role>(new ArrayList<Role>() {
				{
					add(roleRepository.findById(1L).get());
				}
			}));
			;
			userRepository.save(userSave);
			return userSave;
		} else {
			throw new Error("Elemento non salvato");
		}
	}
	
	 public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
	        User user = userRepository.findByEmail(email).get();
	        if (user != null) {
	            user.setResetPasswordToken(token);
	            userRepository.save(user);
	        } else {
	            throw new UserNotFoundException("Could not find any user with the email " + email);
	        }
	    }
	     
	    public Optional<User> getByResetPasswordToken(String token) {
	        return userRepository.findByResetPasswordToken(token);
	    }
	     
	    public void updatePassword(User user, String newPassword) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        user.setPassword(encodedPassword);
	         
	        user.setResetPasswordToken(null);
	        userRepository.save(user);
	    }

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	protected void beforeSave(User obj) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnInsert(User objectToInsert) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnUpdate(User objectToUpdate) {
		// TODO Auto-generated method stub

	}

}
