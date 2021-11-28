package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "restaurants")
@Getter
@Setter
public class RestaurantOutputDto extends RepresentationModel<RestaurantOutputDto> {

    /*
        Projeção de recursos com @JsonView do Jackson
    */
    //@JsonView({RestaurantView.Summary.class, RestaurantView.SummaryJustName.class})
    @ApiModelProperty(example = "1")
    private Long id;

    //@JsonView({RestaurantView.Summary.class, RestaurantView.SummaryJustName.class})
    @ApiModelProperty(example = "Taco Cancun Gourmet")
    private String name;


    @ApiModelProperty(example = "12.00")
    private BigDecimal deliveryFee;

    //@JsonView(RestaurantView.Summary.class)
    private CuisineOutputDto cuisine;

    private AddressOutputDto address;

    //@JsonView(RestaurantView.Summary.class)
    private Boolean active;

    //@JsonView(RestaurantView.Summary.class)
    private Boolean opened;

    private OffsetDateTime createdDate;

    private OffsetDateTime dateLastUpdate;

}
