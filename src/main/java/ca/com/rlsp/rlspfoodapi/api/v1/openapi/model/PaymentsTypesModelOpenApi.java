package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("PaymentTypesModel")
@Data
public class PaymentsTypesModelOpenApi {

    private PaymentTypesEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("PaymentTypesEmbeddedModelOpenApi")
    @Data
    public class PaymentTypesEmbeddedModelOpenApi  {
        private List<CityOutputDto> cities;

    }
}
