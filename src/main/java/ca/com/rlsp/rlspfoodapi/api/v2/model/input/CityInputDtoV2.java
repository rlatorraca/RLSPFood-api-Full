package ca.com.rlsp.rlspfoodapi.api.v2.model.input;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProvinceInputDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiModel("CityInput")
@Getter
@Setter
public class CityInputDtoV2 {

    @ApiModelProperty(example = "1", required = true)
    //@NotNull
    private Long id;

    @ApiModelProperty(example = "Toronto", required = true)
    @NotBlank
    private String nameCity;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long idProvince;


}
