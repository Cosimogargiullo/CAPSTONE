package it.epicode.ecommerce.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="carts")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(	name = "cart_product_mapping", 
				joinColumns = @JoinColumn(name = "cart_id"), 
				inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> product = new ArrayList<>();
	
//	@ElementCollection
//    @CollectionTable(name = "cart_product_mapping", 
//      joinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")})
//    @MapKeyColumn(name = "product_id")
//    @Column(name = "price")
//	private final Map<Product, Integer> products = new HashMap<Product, Integer>();
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	public List<Product> addToCart(Product product) {
		this.product.add(product);
		return this.product;
	}
	
	public List<Product> remFromCart(Product product) {
		this.product.remove(product);
		return this.product;
	}
	
}
