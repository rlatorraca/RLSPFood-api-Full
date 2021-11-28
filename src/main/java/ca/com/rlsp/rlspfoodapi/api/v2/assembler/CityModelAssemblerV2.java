package ca.com.rlsp.rlspfoodapi.api.v2.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.controller.CityControllerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.links.BuildLinksV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CityOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityOutputDtoV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinksV2 buildLinks;


    /* Obrigatorio por estender RepresentationModelAssemblerSupport*/
    public CityModelAssemblerV2() {
        super(CityControllerV2.class, CityOutputDtoV2.class);
    }

    /*
            Convert MODEL -> DTO para PUT
        */
    public CityOutputDtoV2 fromControllerToOutput(City city) {

        return modelMapper.map(city, CityOutputDtoV2.class); // Mas o mapeamento substituindo o codigo acima
    }

    @Override // override a classe de RepresentationModelAssemblerSupport
    public CityOutputDtoV2 toModel(City city) {
        /* Usa o RepresentationModelAssemblerSupport<> que ja implementa _self link*/
        CityOutputDtoV2 cityOutputDtoV2 = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityOutputDtoV2);

        // Adiciona os links no recurso automaticamente (Standard)

        cityOutputDtoV2.add(buildLinks.getLinkToCities("cities"));


        return cityOutputDtoV2;
    }

    @Override
    public CollectionModel<CityOutputDtoV2> toCollectionModel(Iterable<? extends City> cities) {
        return super.toCollectionModel(cities).add(buildLinks.getLinkToCities());
        //return super.toCollectionModel(cities).add(linkTo(CityController.class).withSelfRel());
    }
}
