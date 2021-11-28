package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderFilterInputDto {

    @ApiModelProperty(example = "1", value = "Client Id filter to search")
    private Long userId;

    @ApiModelProperty(example = "1", value = "Restaurant Id filter to search")
    private Long restaurantId;

    @ApiModelProperty(example = "2019-10-30T00:00:00Z",
            value = "Data/hora de criação inicial para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime createdDateStart;

    @ApiModelProperty(example = "2019-10-30T00:00:00Z",
            value = "Data/hora de criação inicial para filtro da pesquisa")
    @DateTimeFormat(iso = ISO.DATE_TIME)
    private OffsetDateTime createdDateEnd;
}
