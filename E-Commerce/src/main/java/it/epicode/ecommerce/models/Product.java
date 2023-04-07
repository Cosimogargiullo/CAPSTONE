package it.epicode.ecommerce.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private double price;
	@Column(columnDefinition = "text", length = 10485760)
	private String description;
	private String img;
	private double rate;
	private int rateCount;
    @Column(columnDefinition = "boolean default false")
	private boolean favBtn = false;

//	NULLABLE False
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "shop_id", nullable = true)
	private Shop shop;
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "favourite_id", nullable = true)
//	private Favourite favourite;
	
	@ManyToMany
	@JoinTable(	name = "product_category_mapping", 
				joinColumns = @JoinColumn(name = "product_id"), 
				inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<ProductCategory> category = new HashSet<>();	
}
