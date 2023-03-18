package it.epicode.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import it.epicode.ecommerce.models.Role;
import it.epicode.ecommerce.repositories.RoleRepository;

@Service
public class RoleService  extends AbstractService<Role, Long> {

	@Autowired
	RoleRepository roleRepo;

	@Override
	protected JpaRepository<Role, Long> getRepository() {
		return roleRepo;
	}
	
	@Override
	protected void beforeSave(Role obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnInsert(Role objectToInsert) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateOnUpdate(Role objectToUpdate) {
		// TODO Auto-generated method stub
		
	}
}
