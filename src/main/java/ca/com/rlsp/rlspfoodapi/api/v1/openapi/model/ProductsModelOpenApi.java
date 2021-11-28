package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProductOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("ProductsModel")
@Data
public class ProductsModelOpenApi {

    private ProductsEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("ProductsEmbeddedModelOpenApi")
    @Data
    public class ProductsEmbeddedModelOpenApi  {
        private List<ProductOutputDto> products;

    }
}
