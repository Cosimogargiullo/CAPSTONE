package it.epicode.ecommerce.services;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public abstract class AbstractService<T,ID> {

	protected abstract JpaRepository<T, ID> getRepository();
	
	protected abstract void validateOnInsert(T objectToInsert);

	protected abstract void validateOnUpdate(T objectToUpdate);
	
	public T save(T objectToSave) {
		log.info("Inserting object: {}", objectToSave);
		
		T result = getRepository().save(objectToSave);
		
		log.info("Inserted object: {}", result);

		return result;
	}
	
	public T insert(T objectToInsert) {
		log.info("Inserting object: {}", objectToInsert);

		validateOnInsert(objectToInsert);
		
		beforeSave(objectToInsert);

		T result = getRepository().save(objectToInsert);

		log.info("Inserted object: {}", result);

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
			throw new Error("Elemento con id: " + id + "non presente");
		}
	}

	protected abstract void beforeSave(T obj);
}
