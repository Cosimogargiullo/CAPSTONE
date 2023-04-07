package it.epicode.ecommerce.services;

import java.util.ArrayList;
import java.util.Iterator;
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
import it.epicode.ecommerce.repositories.ProductRepository;

@Service
public class ProductService extends AbstractService<Product, Long> {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@Override
	protected JpaRepository<Product, Long> getRepository() {
		return productRepository;
	}

	public Optional<Product> findById(Long id) throws EcommerceExceptionNotFound {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return product;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product with id " + id);
		}
	}

	public List<Product> findByKeyword(String keyword) {
		String[] search = keyword.split(" ");
		List<Product> results = new ArrayList<>();
		for (int i = 0; i < search.length; i++) {
			List<Product> get = productRepository.findAllByKeywordLike(search[i]);
			if (get.size() > 0) {
				get.forEach(e -> {
					if (e != null) {
						results.add(e);
					}
				});
			}
		}

		return results;
	}

	public List<Product> findByCategory(String category) {
		ProductCategory pc = productCategoryService.findByName(category).get();
		List<Product> product = productRepository.findAll().stream().filter(e -> e.getCategory().contains(pc)).toList();
		return product;
	}
//	public List<Product> findByCategory(String category) throws EcommerceExceptionNotFound {
//		ProductCategory pc = productCategoryService.findByName(category).get();
//		List<Product> product = productRepository.findAll().stream().filter(e-> e.getCategory().contains(pc)).toList();
//		if (product.size()>0) {
//			return product;
//		} else {
//			throw new EcommerceExceptionNotFound("Could not find any product with category " + category);
//		}
//	}

	public List<Product> findAll() throws EcommerceExceptionNotFound {
		List<Product> product = productRepository.findAll();
		if (product.size() > 0) {
			return product;
		} else {
			throw new EcommerceExceptionNotFound("Could not find any product");
		}
	}

	public Product createProduct(Product obj, Long id) throws EcommerceExceptionNotValid, EcommerceExceptionNotFound {
		Optional<Shop> shop = shopService.findById(id);
		obj.setShop(shop.get());
		if (!shop.isPresent()) {
			throw new EcommerceExceptionNotFound("Could not find shop with id " + id);
		}
		Set<ProductCategory> c = productCategoryService.insertCategories(obj.getCategory());
		obj.setCategory(c);
		Product save = this.insert(obj);
		return save;
	}

	public Product updateInfo(Long id, Product p) throws EcommerceExceptionNotFound, EcommerceExceptionNotValid {
		Optional<Product> productResult = productRepository.findById(id);

		if (productResult.isPresent()) {

			Product productUpdate = productResult.get();
			productUpdate.setTitle(p.getTitle());
			productUpdate.setPrice(p.getPrice());
			productUpdate.setDescription(p.getDescription());
			productUpdate.setImg(p.getImg());

			Set<ProductCategory> c = productCategoryService.insertCategories(p.getCategory());
			productUpdate.setCategory(c);
			productRepository.save(productUpdate);

			return productUpdate;

		} else {
			throw new EcommerceExceptionNotFound("Could not find any product to change info to!");
		}
	}

	@Override
	protected void beforeSave(Product obj) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnInsert(Product objectToInsert) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateOnUpdate(Product objectToUpdate) {
		// TODO Auto-generated method stub

	}

}
