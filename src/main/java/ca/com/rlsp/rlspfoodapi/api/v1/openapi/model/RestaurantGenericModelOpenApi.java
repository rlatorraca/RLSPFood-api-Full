package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/*
    APENAS para fim de documentaoca na Swagger
 */
@ApiModel("GenericRestaurant")
@Setter
@Getter
public class RestaurantGenericModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Pizza na Pedra")
    private String name;

    @ApiModelProperty(example = "10.00")
    private BigDecimal deliveryFee;


    private CuisineOutputDto cuisine;
}
