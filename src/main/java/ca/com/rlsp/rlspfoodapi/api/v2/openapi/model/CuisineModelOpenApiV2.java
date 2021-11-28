package ca.com.rlsp.rlspfoodapi.api.v2.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CuisineOutputDtoV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("CuisinesModel")
@Setter
@Getter
//public class CuisineModelOpenApi extends PageModelOpenApi<CuisineOutputDto>{
public class CuisineModelOpenApiV2 {

    private CuisinesEmbeddedModelOpenApi _embedded;
    private Link _links;
    private PagedModelOpenApiV2 page;


    @ApiModel("CuisinesEmbeddedModelOpenApi")
    @Data
    public class CuisinesEmbeddedModelOpenApi  {
        private List<CuisineOutputDtoV2> cuisines;

    }

}
