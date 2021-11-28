package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInputDto {

    private Long id;

    @ApiModelProperty(example = "Hamburguer Gourmet", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "15.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal deliveryFee;

    @Valid
    @NotNull
    private CuisineInputDto cuisine;

    @Valid
    @NotNull
    private AddressInputDto address;



}
