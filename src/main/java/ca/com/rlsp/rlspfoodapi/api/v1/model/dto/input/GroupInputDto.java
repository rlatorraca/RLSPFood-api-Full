package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GroupInputDto {

    @ApiModelProperty(example = "Manager", required = true)
    @NotBlank
    private String name;
}
