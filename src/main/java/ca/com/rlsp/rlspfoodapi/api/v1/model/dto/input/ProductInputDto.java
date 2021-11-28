package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInputDto extends ProductInputUpdateStatusDto {

    @ApiModelProperty(example = "Pizza Pantaneira", required = true)
    @NotBlank
    @Column(name = "product_name", nullable = false)
    private String name;

    @ApiModelProperty(example = "Cheese, olive, salted meat, banana, pea", required = true)
    @NotBlank
    @Column(name = "product_description", nullable = false)
    private String description;

    @ApiModelProperty(example = "25.00", required = true)
    @NotNull
    @PositiveOrZero
    @Column(name = "product_price", nullable = false)
    private BigDecimal price;


}
