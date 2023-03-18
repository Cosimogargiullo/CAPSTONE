package it.epicode.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
