package ca.com.rlsp.rlspfoodapi.api.v2.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CityOutputDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelOpenApiV2 {

    private CitiesEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("CitiesEmbeddedModelOpenApi")
    @Data
    public class CitiesEmbeddedModelOpenApi  {
        private List<CityOutputDtoV2> cities;

    }
}
