package it.epicode.ecommerce.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotFound;
import it.epicode.ecommerce.common.exceptions.EcommerceExceptionNotValid;
import it.epicode.ecommerce.models.Product;
import it.epicode.ecommerce.models.ProductCategory;
import it.epicode.ecommerce.models.Shop;
import it.epicode.ecommerce.repositories.ProductCategoryRepository;

@Service
public class ProductCategoryService extends AbstractService<ProductCategory, Long> {

	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Override
	protected JpaRepository<ProductCategory, Long> getRepository() {
		return this.productCategoryRepository;
	}

	public Set<ProductCategory> insertCategories(Set<ProductCategory> category) {
		Set<ProductCategory> results = new HashSet<>();
		category.forEach(c -> {
			Optional<ProductCategory> prodByName = this.productCategoryRepository.findByName(c.getName());
			if (prodByName.isPresent()) {
				results.add(prodByName.get());
			} else {
				ProductCategory c$ = new ProductCategory();
				c$.setName(c.getName());
				this.productCategoryRepository.save(c$);
				results.add(c$);
			}
		});
		return results;
	}

	@Override
	protected void validateOnInsert(ProductCategory objectToInsert) throws EcommerceExceptionNotValid {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnUpdate(ProductCategory objectToUpdate) throws EcommerceExceptionNotValid {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeSave(ProductCategory obj) {
		// TODO Auto-generated method stub

	}

	public List<ProductCategory> findAll() throws EcommerceExceptionNotFound {
		List<ProductCategory> product = productCategoryRepository.findAll();
		if (product.size() > 0) {
			return product;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}

	public Optional<ProductCategory> findByName(String category) {
		Optional<ProductCategory> product = productCategoryRepository.findByName(category);
		return product;

//		public Optional<ProductCategory> findByName(String category) throws EcommerceExceptionNotFound {
//			Optional<ProductCategory> product = productCategoryRepository.findByName(category);
//			if (product.isPresent()) {
//				return product;
//			} else {
//				throw new EcommerceExceptionNotFound("Could not find any product");
//			}

	}

}
