package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantBasicsOutputDto extends RepresentationModel<RestaurantBasicsOutputDto> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "French Bistro")
    private String name;

    @ApiModelProperty(example = "22.00")
    private BigDecimal deliveryFee;

    private CuisineOutputDto cuisine;
}
