package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


/*
    Limitando os campos retornados pela API com @JsonFilter do Jackson
 */
@Relation(collectionRelation = "order")
@ApiModel(value = "Short Order")
//@JsonFilter("orderFilter")
@Setter
@Getter
public class OrderShortOutputDto extends RepresentationModel<OrderShortOutputDto> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String orderCode;

    @ApiModelProperty(example = "200.00")
    private BigDecimal beforeTax;

    @ApiModelProperty(example = "20.00")
    private BigDecimal deliveryFee;

    @ApiModelProperty(example = "270.90")
    private BigDecimal afterTax;

    @ApiModelProperty(example = "0.15")
    private BigDecimal taxes;

    @ApiModelProperty(example = "CREATED")
    private String status;


    @ApiModelProperty(example = "2021-10-01T20:00:00Z")
    private OffsetDateTime createdDate;

    private RestaurantJustNamesOutputDto restaurant;

    private UserOutputDto user;


}
