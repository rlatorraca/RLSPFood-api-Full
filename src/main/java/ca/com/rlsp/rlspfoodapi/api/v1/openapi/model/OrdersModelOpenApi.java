package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.OrderOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("OrdersModel")
@Setter
@Getter
//public class CuisineModelOpenApi extends PageModelOpenApi<CuisineOutputDto>{
public class OrdersModelOpenApi {

    private OrdersEmbeddedModelOpenApi _embedded;
    private Link _links;
    private PagedModelOpenApi page;


    @ApiModel("OrdersShortEmbeddedModelOpenApi")
    @Data
    public class OrdersEmbeddedModelOpenApi  {
        private List<OrderOutputDto> orders;

    }

}
