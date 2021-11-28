package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductOutputDto extends RepresentationModel<ProductOutputDto> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Pizza Pantaneira", required = true)
    private String name;

    @ApiModelProperty(example = "Cheese, olive, salted meat, banana, pea", required = true)
    private String description;

    @ApiModelProperty(example = "25.00", required = true)
    private BigDecimal price;

    @ApiModelProperty(example = "true", required = true)
    private Boolean active;

}
