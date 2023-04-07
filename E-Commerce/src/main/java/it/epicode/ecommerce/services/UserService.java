package it.epicode.ecommerce.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.Favourite;
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
	
	@Autowired
	FavouriteService favouriteService;

	@Override
	protected JpaRepository<User, Long> getRepository() {
		return userRepository;
	}

	public Optional<User> findById(Long id) throws EcommerceExceptionNotFound {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any user with id " + id);
		}
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

	public Optional<User> getByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	private void testUniqueUsernameEmail(User user, boolean update) throws EcommerceExceptionNotValid {
		Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
		Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());

		if (userByUsername.isPresent() || userByEmail.isPresent()) {

			if (update) {
				if (!userByUsername.get().getId().equals(user.getId())) {
					throw new EcommerceExceptionNotValid("User with Email: " + user.getEmail() + " and Username: "
							+ user.getUsername() + " already present");
				}
			} else {
				throw new EcommerceExceptionNotValid("User with Email: " + user.getEmail() + " and Username: "
						+ user.getUsername() + " already present");
			}
		}
	}

	public void updateResetPasswordToken(String token, String email) throws EcommerceExceptionNotFound {
		User user = userRepository.findByEmail(email).get();
		if (user != null) {
			user.setResetPasswordToken(token);
			userRepository.save(user);
		} else {
			throw new EcommerceExceptionNotFound("Could not find any user with the email " + email);
		}
	}

	public User updatePassword(Long id, User user) throws EcommerceExceptionNotFound {
		Optional<User> userResult = userRepository.findById(id);

		if (userResult.isPresent()) {
			User userUpdate = userResult.get();
			userUpdate.setPassword(pe.encode(user.getPassword()));
			userUpdate.setResetPasswordToken(null);
			userRepository.save(userUpdate);
			return userUpdate;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any user to change password to!");
		}
	}

	public User updateInfo(Long id, User user) throws EcommerceExceptionNotFound, EcommerceExceptionNotValid {
		Optional<User> userResult = userRepository.findById(id);
		Optional<User> userUsernameResult = userRepository.findByUsername(user.getUsername());
		Optional<User> userEmailResult = userRepository.findByEmail(user.getEmail());

		if (userResult.isPresent()) {

			if (userUsernameResult.isPresent()) {
				if (!(userResult.get().getId() == userUsernameResult.get().getId())) {
					throw new EcommerceExceptionNotValid("USERNAME");
				}
			}

			if (userEmailResult.isPresent()) {
				if (!(userResult.get().getId() == userEmailResult.get().getId())) {
					throw new EcommerceExceptionNotValid("EMAIL");
				}
			}
			User userUpdate = userResult.get();
			userUpdate.setName(user.getName());
			userUpdate.setSurname(user.getSurname());
			userUpdate.setUsername(user.getUsername());
			userUpdate.setEmail(user.getEmail());
			userRepository.save(userUpdate);

			return userUpdate;

		} else {
			throw new EcommerceExceptionNotFound("Could not find any user to change info to!");
		}
	}

//	SING UP FUNCTION
//	public User singupUser(User user) {
////		CONTROL THAT NO USER WITH EMAIL/USERNAME ALREADY EXIST
//		Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
//		Optional<User> userByEmail = userRepository.findByUsername(user.getUsername());
//
//		if (!(userByUsername.isPresent() || userByEmail.isPresent())) {
//			User userSave = new User();
//			userSave.setName(user.getName());
//			userSave.setSurname(user.getSurname());
//			userSave.setUsername(user.getUsername());
//			userSave.setEmail(user.getEmail());
//			userSave.setPassword(pe.encode(user.getPassword()));
//			userSave.setRoles(new HashSet<Role>(new ArrayList<Role>() {
//				{
//					add(roleRepository.findById(1L).get());
//				}
//			}));
//			;
//			userRepository.save(userSave);
//			return userSave;
//		} else {
//			throw new Error("Elemento non salvato");
//		}
//	}

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

	@Override
	protected void beforeSave(User user) {
		user.setPassword(pe.encode(user.getPassword()));
		user.getFavourite().setUser(user);
		user.getCart().setUser(user);
		user.setImg(
				"https://www.uninsubria.it/sites/default/files/styles/medium/public/transgender.png?itok=ifVuS_IKÏ");
		user.setRoles(new HashSet<Role>(new ArrayList<Role>() {
			{
				add(roleRepository.findById(1L).get());
			}
		}));
	}

	@Override
	protected void validateOnInsert(User objectToInsert) throws EcommerceExceptionNotValid {
		testUniqueUsernameEmail(objectToInsert, false);
	}

	@Override
	protected void validateOnUpdate(User objectToUpdate) throws EcommerceExceptionNotValid {
		// TODO Auto-generated method stub

	}

}
