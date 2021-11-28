package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.UserOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("RestaurantsBasicsModel")
@Data
public class UsersModelOpenApi {

    private UsersBasicsEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("UsersBasicsEmbeddedModelOpenApi")
    @Data
    public class UsersBasicsEmbeddedModelOpenApi  {
        private List<UserOutputDto> users;

    }
}
