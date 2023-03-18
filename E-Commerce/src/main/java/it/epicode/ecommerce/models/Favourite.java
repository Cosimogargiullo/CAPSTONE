package it.epicode.ecommerce.models;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	
	@ElementCollection
    @CollectionTable(name = "favourite_product_mapping", 
      joinColumns = {@JoinColumn(name = "favourite_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "product_id")
    @Column(name = "price")
	private final Map<Product, Integer> products = new HashMap<Product, Integer>();
	
	@OneToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;
}
