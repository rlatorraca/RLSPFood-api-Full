package ca.com.rlsp.rlspfoodapi.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@ApiModel("Pageable")
@Getter
@Setter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "1", value="Number of pages (starts in 0)")
    private String page;

    @ApiModelProperty(example = "5", value="Number of elements in each page")
    private String size;

    @ApiModelProperty(example = "name,asc", value="Number of pages (starts in 0)")
    private Collection<String> sort;

}
