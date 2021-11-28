package ca.com.rlsp.rlspfoodapi.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
public class
OrderFilter {


    @ApiModelProperty(example = "1", value = "Restaurant ID to filter in a search")
    private Long userId;

    @ApiModelProperty(example = "1", value = "estaurant ID to filter in a search")
    private Long restaurantId;

    @ApiModelProperty(example = "2021-11-01T10:00:00Z",
            value = "Created date to filter in a search on format Date/time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdDateStart;

    @ApiModelProperty(example = "2021-11-01T10:00:00Z",
            value = "Created date to filter in a search on format Date/time")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createdDateEnd;
}
