package ca.com.rlsp.rlspfoodapi.domain.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbl_product_photo")
public class ProductPhoto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Long id;

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Faz com que PRODUCT seja mapeado usando @id dessa classe (que no caso ja eh product_id
    private Product product;

    public Long getRestaurantId() {
        if(this.getProduct() != null) {
            return this.getProduct().getRestaurant().getId();
        }

        return null;
    }
}
