package it.epicode.ecommerce.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public abstract class AbstractService<T,ID> {

	protected abstract JpaRepository<T, ID> getRepository();
	
	protected abstract void validateOnInsert(T objectToInsert) throws EcommerceExceptionNotValid;

	protected abstract void validateOnUpdate(T objectToUpdate) throws EcommerceExceptionNotValid;
	
	public T save(T objectToSave) {
//		log.info("Inserting object: {}", objectToSave);
		
		T result = getRepository().save(objectToSave);
		
//		log.info("Inserted object: {}", result);

		return result;
	}
	
	public T insert(T objectToInsert) throws EcommerceExceptionNotValid {
//		log.info("Inserting object: {}", objectToInsert);

		validateOnInsert(objectToInsert);
		
		beforeSave(objectToInsert);

		T result = getRepository().save(objectToInsert);

//		log.info("Inserted object: {}", result);

		return result;

	}
	
	public T update(T obj, ID id) throws Error{
		Optional<T> repoObj = getRepository().findById(id);
		log.info("Updating object: {}", obj);
		if (repoObj.isPresent()) {
			T savedObject = repoObj.get();
			beforeSave(savedObject);
			savedObject = getRepository().save(savedObject);
			log.info("Updated object: {}", savedObject);
			return savedObject;
		} else {
			throw new Error("Element id: " + id + "not present");
		}
	}
	
	public void delete(ID id) throws EcommerceExceptionNotFound {
		Optional<T> repoObj = getRepository().findById(id);
		
		if (repoObj.isPresent()) {
			getRepository().deleteById(id);
		} else {
			throw new EcommerceExceptionNotFound("Element id: " + id + "not present");
		}
	}

	protected abstract void beforeSave(T obj);
}
