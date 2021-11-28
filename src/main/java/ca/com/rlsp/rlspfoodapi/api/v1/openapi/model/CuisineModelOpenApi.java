package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
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
public class CuisineModelOpenApi {

    private CuisinesEmbeddedModelOpenApi _embedded;
    private Link _links;
    private PagedModelOpenApi page;


    @ApiModel("CuisinesEmbeddedModelOpenApi")
    @Data
    public class CuisinesEmbeddedModelOpenApi  {
        private List<CuisineOutputDto> cuisines;

    }

}
