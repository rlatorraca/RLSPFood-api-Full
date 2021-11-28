package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("RestaurantsModel")
@Data
public class RestaurantsModelOpenApi {

    private RestaurantsEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("RestaurantsEmbeddedModelOpenApi")
    @Data
    public class RestaurantsEmbeddedModelOpenApi  {
        private List<RestaurantOutputDto> restaurants;

    }
}
