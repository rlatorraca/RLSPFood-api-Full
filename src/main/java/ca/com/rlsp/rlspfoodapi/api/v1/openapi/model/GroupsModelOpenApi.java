package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.GroupOutputDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("GroupsModel")
@Getter
@Setter
public class GroupsModelOpenApi {

    private GroupsEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("GroupsEmbeddedModelOpenApi")
    @Data
    public class GroupsEmbeddedModelOpenApi  {
        private List<GroupOutputDto> groups;

    }
}
