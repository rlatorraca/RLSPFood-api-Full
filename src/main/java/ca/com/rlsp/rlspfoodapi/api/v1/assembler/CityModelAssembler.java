package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.CityController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CityInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProvinceInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;


@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityOutputDto> {



    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    /* Obrigatorio por estender RepresentationModelAssemblerSupport*/
    public CityModelAssembler() {
        super(CityController.class, CityOutputDto.class);
    }

    /*
            Convert MODEL -> DTO para PUT
        */
    public CityOutputDto fromControllerToOutput(City city) {

        return modelMapper.map(city, CityOutputDto.class); // Mas o mapeamento substituindo o codigo acima
    }

    @Override // override a classe de RepresentationModelAssemblerSupport
    public CityOutputDto toModel(City city) {
        /* Usa o RepresentationModelAssemblerSupport<> que ja implementa _self link*/
        CityOutputDto cityOutputDto = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityOutputDto);

        // Adiciona os links no recurso automaticamente (Standard)

//        CityOutputDto cityOutputDto = modelMapper.map(city, CityOutputDto.class);
//        cityOutputDto.add(
//                linkTo(methodOn(CityController.class)
//                        .findById(cityOutputDto.getId()))
//                        .withSelfRel()
//        );

        if(rlspFoodSecurity.hasPermissionToQueryCities()){
            cityOutputDto.add(buildLinks.getLinkToCities("cities"));
        }

//        cityOutputDto.add(
//                linkTo(methodOn(CityController.class)
//                        .listAllJson())
//                        .withRel("cities")
//        );

        if(rlspFoodSecurity.hasPermissionToQueryProvinces()){
            cityOutputDto.getProvince().add(buildLinks.getLinkToProvince(cityOutputDto.getProvince().getId()));
        }

//        cityOutputDto.getProvince().add(
//                linkTo(methodOn(ProvinceController.class)
//                        .findById(cityOutputDto.getProvince().getId()))
//                        .withSelfRel()
//        );

        return cityOutputDto;
    }

    @Override
    public CollectionModel<CityOutputDto> toCollectionModel(Iterable<? extends City> cities) {
        CollectionModel<CityOutputDto> collectionModel = super.toCollectionModel(cities);

        if(rlspFoodSecurity.hasPermissionToQueryCities()){
            collectionModel.add(buildLinks.getLinkToCities());
        }

        return  collectionModel;

        //return super.toCollectionModel(cities).add(buildLinks.getLinkToCities());
        //return super.toCollectionModel(cities).add(linkTo(CityController.class).withSelfRel());
    }

    /*
            Convert MODEL -> DTO
        */
    public CityInputDto fromControllerToInput(City city) {
        CityInputDto cityInputDTO = new CityInputDto();

        ProvinceInputDto provinceInputDTO = new ProvinceInputDto();
        provinceInputDTO.setId(city.getProvince().getId());
        provinceInputDTO.setName(city.getProvince().getName());

        cityInputDTO.setId(city.getId());
        cityInputDTO.setName(city.getName());
        cityInputDTO.setProvince(provinceInputDTO);

        return cityInputDTO;
    }


    /*
        Convert MODEL -> DTO (list GET)

        ** ja existe um CollectionModel dentro de RepresentationModelAssemblerSupport
    */
//    public List<CityOutputDto> fromControllerToOutputList(List<City> cities){
//        return cities.stream()
//                .map(city -> fromControllerToOutput(city))
//                .collect(Collectors.toList());
//    }
}
