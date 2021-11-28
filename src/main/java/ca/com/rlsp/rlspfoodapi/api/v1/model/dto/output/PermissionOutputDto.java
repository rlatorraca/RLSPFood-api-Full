package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionOutputDto extends RepresentationModel<PermissionOutputDto> {

    @ApiModelProperty(example = "1")
    private String id;

    @ApiModelProperty(example = "FULL CLIENT")
    private String name;

    @ApiModelProperty(example = "Full access in all client resources")
    private String description;

}
