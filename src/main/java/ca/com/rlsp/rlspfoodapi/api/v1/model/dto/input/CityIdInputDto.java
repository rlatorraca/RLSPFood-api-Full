package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityIdInputDto {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;

}
