package it.epicode.ecommerce.repositories;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> findByName(String nome, Pageable pageable);

	Optional<User> findByUsername(String nome);
	
	Optional<User> findByEmail(String email);

	Optional<User> findByResetPasswordToken(String token);
	
	void deleteById(Long id);
}
