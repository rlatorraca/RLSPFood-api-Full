package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "provinces")
@Getter
@Setter
public class ProvinceOutputDto extends RepresentationModel<ProvinceOutputDto> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Alberta")
    private String name;

    private Long taxId;
}
