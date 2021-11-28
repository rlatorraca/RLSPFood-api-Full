package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "order")
@ApiModel(value = "Order")
@Setter
@Getter
public class OrderOutputDto extends RepresentationModel<OrderOutputDto> {



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

    //@ApiModelProperty (example = "2021-10-01T20:00:00Z")
    private OffsetDateTime createdDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime startedDate;
    private OffsetDateTime onTheOvenDate;
    private OffsetDateTime readyDate;
    private OffsetDateTime onTheRoadDate;
    private OffsetDateTime deliveryDate;
    private OffsetDateTime canceledDate;

    private AddressOutputDto addressDelivery;
    private PaymentTypeOutputDto paymentType;
    private RestaurantJustNamesOutputDto restaurant;

    private UserOutputDto user;

    @ApiModelProperty(example = "Rayan McDonalds")
    private String nameUser;
    private List<ItemOrderOutputDto> orderItems;

}
