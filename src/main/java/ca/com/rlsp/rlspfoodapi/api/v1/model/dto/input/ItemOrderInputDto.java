package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
@Getter
@Setter
public class ItemOrderInputDto {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long productId;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @ApiModelProperty(example = "No spicy, no cheese ")
    private String comments;
    //private BigDecimal unitPrice;
    //private BigDecimal totalPrice;
    //private String productName;
}
