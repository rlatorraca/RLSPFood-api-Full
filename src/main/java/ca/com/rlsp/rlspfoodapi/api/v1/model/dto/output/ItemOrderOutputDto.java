package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "item_order")
@Getter
@Setter
public class ItemOrderOutputDto extends RepresentationModel<ItemOrderOutputDto> {

    @ApiModelProperty(example = "1")
    private Integer quantity;
    @ApiModelProperty(example = "80.00")
    private BigDecimal unitPrice;
    @ApiModelProperty(example = "105.00")
    private BigDecimal totalPrice;
    @ApiModelProperty(example = "No spicy")
    private String comments;
    @ApiModelProperty(example = "1")
    private Long productId;
    @ApiModelProperty(example = "Cheese poutine")
    private String productName;
}
