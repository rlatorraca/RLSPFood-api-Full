package ca.com.rlsp.rlspfoodapi.api.v2.model.output;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cuisines")
//@ApiModel(value = "CuisineOutput", description = "Output to City representation")
@Getter
@Setter
public class CuisineOutputDtoV2 extends RepresentationModel<CuisineOutputDtoV2> {

    @ApiModelProperty(example = "1")
    //@JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brazilian")
    //@JsonView(RestaurantView.Summary.class)
    private String name;
}
