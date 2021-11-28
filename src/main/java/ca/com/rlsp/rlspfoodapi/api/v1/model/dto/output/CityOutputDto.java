package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/*
    RepresentationModel => from Spring HATEAOS
 */
@Relation(collectionRelation = "cities") // Muda o nome da representacao dentro do JSON
//@ApiModel(value = "City", description = "Output to City representation")
@Getter
@Setter
public class CityOutputDto  extends RepresentationModel<CityOutputDto> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(value = "A name to the city", example = "Calgary")
    private String name;

    private ProvinceOutputDto province;
}
