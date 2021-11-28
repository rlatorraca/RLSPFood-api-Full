package ca.com.rlsp.rlspfoodapi.api.v2.model.input;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.TaxProvinceInputDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("ProvinceInput")
@Getter
@Setter
public class ProvinceInputDtoV2 {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Alberta")
    private String name;

    @ApiModelProperty(example = "1")
    private TaxProvinceInputDto tax;
}
