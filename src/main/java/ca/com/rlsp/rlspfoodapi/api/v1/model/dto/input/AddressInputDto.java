package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInputDto {

    @ApiModelProperty(example = "A1B 2C4", required = true)
    @NotBlank
    private String postalcode;

    @ApiModelProperty(example = "No name street", required = true)
    @NotBlank
    private String street;

    @ApiModelProperty(example = "12345", required = true)
    @NotBlank
    private String number;


    @ApiModelProperty(example = "Apto 101")
    private String complement;

    @ApiModelProperty(example = "Downtown", required = true)
    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdInputDto city;

}
