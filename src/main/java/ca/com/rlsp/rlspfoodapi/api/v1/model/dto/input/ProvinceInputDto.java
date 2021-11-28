package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProvinceInputDto {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Alberta")
    private String name;

    @ApiModelProperty(example = "1")
    private TaxProvinceInputDto tax;
}
