package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cuisines")
@Getter
@Setter
public class CuisineOutputDto extends RepresentationModel<CuisineOutputDto> {

    @ApiModelProperty(example = "1")
    //@JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brazilian")
    //@JsonView(RestaurantView.Summary.class)
    private String name;
}
