package it.epicode.ecommerce.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="favourites")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany
	@JoinTable(	name = "favourite_product_mapping", 
				joinColumns = @JoinColumn(name = "favourite_id"), 
				inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> product = new ArrayList<>();
	
	@JsonIgnore
	@OneToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	private List<Product> addFavourite(Product product) {
		product.setFavBtn(true);
		this.product.add(product);
		return this.product;
	}
	
	private List<Product> removeFavourite(Product product) {
		product.setFavBtn(false);
		this.product.remove(product);
		return this.product;
	}
	
	private boolean verifyFavourite(Product product) {
		boolean res = this.product.contains(product);
		return res;
	}
	
	public List<Product> toggleFavourite(Product product) {
		boolean b = this.verifyFavourite(product);
		if (b) {
			return this.removeFavourite(product);
		} else {
			return this.addFavourite(product);
		}
	}
	
	
	
}
