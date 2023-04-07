package it.epicode.ecommerce.models;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
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
@Table(name="shops")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Shop {

	@Id
	@Column(name = "user_id")
	private Long id;
	private String shopName;
	@Column(columnDefinition = "text", length = 10485760)
	private String img;
	
	@OneToMany(mappedBy="shop")
    private Set<Product> product;
	
	@JsonIgnore
	@OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
	private User user;
}
