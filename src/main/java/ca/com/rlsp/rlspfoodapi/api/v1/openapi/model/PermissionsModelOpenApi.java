package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import ca.com.rlsp.rlspfoodapi.domain.model.Permission;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@ApiModel("PermissionModel")
@Getter
@Setter
public class PermissionsModelOpenApi {

    private PermissionsEmbeddedModelOpenApi _embedded;
    private Link _links;


    @ApiModel("PermissionsEmbeddedModelOpenApi")
    @Data
    public class PermissionsEmbeddedModelOpenApi  {
        private List<Permission> permissions;

    }
}
