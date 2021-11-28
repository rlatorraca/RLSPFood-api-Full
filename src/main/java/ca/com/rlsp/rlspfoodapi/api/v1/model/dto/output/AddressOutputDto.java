package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressOutputDto {

    @ApiModelProperty(example = "A1B 2C4", required = true)
    private String postalcode;

    @ApiModelProperty(example = "No name street", required = true)
    private String street;

    @ApiModelProperty(example = "12345", required = true)
    private String number;

    @ApiModelProperty(example = "Apto 101")
    private String complement;

    @ApiModelProperty(example = "Downtown", required = true)
    private String district;

    private CityShortOutputDto city;

}
