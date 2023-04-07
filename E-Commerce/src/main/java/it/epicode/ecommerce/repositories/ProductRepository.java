package it.epicode.ecommerce.repositories;

import org.springframework.stereotype.Repository;
import it.epicode.ecommerce.models.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	
//	List<Product> findAllByTitleLike(String keyword);
	@Query(value="SELECT * FROM products p JOIN product_category_mapping pc ON p.id = pc.product_id JOIN product_categories c ON c.id = pc.category_id WHERE LOWER( p.title ) LIKE %:keyword% OR LOWER( p.description ) LIKE %:keyword% OR LOWER( c.name ) LIKE %:keyword%", nativeQuery=true)
	List<Product> findAllByKeywordLike(@Param("keyword") String keyword);
	
	

}
