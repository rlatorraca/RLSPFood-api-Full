package ca.com.rlsp.rlspfoodapi.api.v2.model.output;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProvinceOutputDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/*
    RepresentationModel => from Spring HATEAOS
 */
@Relation(collectionRelation = "cities", itemRelation = "city", value = "city") // Muda o nome da representacao dentro do JSON
//@ApiModel(value = "CityOutput", description = "Output to City representation")
@Getter
@Setter
public class CityOutputDtoV2 extends RepresentationModel<CityOutputDtoV2>  {

    @ApiModelProperty(example = "1")
    private Long idCity;

    @ApiModelProperty(value = "A name to the city", example = "Calgary")
    private String nameCity;


    private Long idProvince;

    private String nameProvince;
}
