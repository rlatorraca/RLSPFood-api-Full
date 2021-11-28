package ca.com.rlsp.rlspfoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_orderitem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer quantity;
    @Column(name = "unitprice")
    private BigDecimal unitPrice;
    @Column(name = "totalprice")
    private BigDecimal totalPrice;
    private String comments;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public void calculateTotalPriceForItems() {
        BigDecimal unitaryPrice = this.getUnitPrice();
        Integer quantity = this.getQuantity();

        if (unitaryPrice == null) {
            unitaryPrice = BigDecimal.ZERO;
        }

        if (quantity == null) {
            quantity = 0;
        }

        this.setTotalPrice(unitaryPrice.multiply(new BigDecimal(quantity)));
    }
}
