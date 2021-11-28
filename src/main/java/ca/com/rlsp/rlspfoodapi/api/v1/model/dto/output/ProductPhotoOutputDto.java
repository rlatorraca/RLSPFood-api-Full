package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "photos")
@Getter
@Setter
public class ProductPhotoOutputDto extends RepresentationModel<ProductPhotoOutputDto> {


    @ApiModelProperty(example = "59a0dd1e-8dea-4cac-b5f8-34d82bdaa099_1_1_picanha.jpeg")
    private String fileName;
    @ApiModelProperty(example = "Top Sirloin cap")
    private String description;
    @ApiModelProperty(example = "image/jpeg")
    private String contentType;
    @ApiModelProperty(example = "1024")
    private Long size;

}
