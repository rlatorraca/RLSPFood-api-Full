package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProvinceOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("ProvincesModel")
@Data
public class ProvincesModelApi {

    private ProvincesEmbeddedModelOpenApi _embedded;
    private Link _links;

    @ApiModel("ProvincesEmbeddedModelOpenApi")
    @Data
    public class ProvincesEmbeddedModelOpenApi  {
        private List<ProvinceOutputDto> provinces;

    }
}
