package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelOpenApi {

    private CitiesEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("CitiesEmbeddedModelOpenApi")
    @Data
    public class CitiesEmbeddedModelOpenApi  {
        private List<CityOutputDto> cities;

    }
}
