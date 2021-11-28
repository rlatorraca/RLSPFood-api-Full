package ca.com.rlsp.rlspfoodapi.api.v2.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.CuisineController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.RestaurantInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.links.BuildLinksV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CuisineOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuisineModelAssemblerV2 extends RepresentationModelAssemblerSupport<Cuisine, CuisineOutputDtoV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinksV2 buildLinks;

    public CuisineModelAssemblerV2() {
        super(CuisineController.class, CuisineOutputDtoV2.class);
    }


    /*
        Convert MODEL -> DTO para PUT
    */
    public CuisineOutputDtoV2 fromControllerToOutput(Cuisine cuisine) {

        return modelMapper.map(cuisine, CuisineOutputDtoV2.class); // Mas o mapeamento substituindo o codigo acima
    }


//    /*
//        Convert MODEL -> DTO (para POST)
//    */
//    public RestaurantOutputDto fromControllerToOutputPost(Restaurant restaurant) {
//
//        return modelMapper.map(restaurant, RestaurantOutputDto.class); // Mas o mapeamento substituindo o codigo acima
//    }
//
//    /*
//        Convert MODEL -> DTO
//    */
//    public RestaurantInputDto fromControllerToInput(Restaurant restaurant) {
//        System.out.println(restaurant.getCuisine().getId());
//        System.out.println(restaurant.getDeliveryFee());
//        System.out.println(restaurant.getName());
//
//        CuisineInputDto cuisineDTO = new CuisineInputDto();
//        cuisineDTO.setId(restaurant.getCuisine().getId());
//        cuisineDTO.setName(restaurant.getCuisine().getName());
//
//        RestaurantInputDto restaurantDTO = new RestaurantInputDto();
//        restaurantDTO.setId(restaurant.getId());
//        restaurantDTO.setName(restaurant.getName());
//        restaurantDTO.setDeliveryFee(restaurant.getDeliveryFee());
//        restaurantDTO.setCuisine(cuisineDTO);
//        return restaurantDTO;
//    }


    /*
        Convert MODEL -> DTO (list GET)
    */
    public List<CuisineOutputDtoV2> fromControllerToOutputList(List<Cuisine> cuisines){
        return cuisines.stream()
                .map(cuisine -> fromControllerToOutput(cuisine))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionModel<CuisineOutputDtoV2> toCollectionModel(Iterable<? extends Cuisine> cuisine) {
        return super.toCollectionModel(cuisine)
                .add(buildLinks.getLinkToCuisines());
    }

    @Override
    public CuisineOutputDtoV2 toModel(Cuisine cuisine) {

        /* Usa o RepresentationModelAssemblerSupport<> que ja implementa _self link*/
        CuisineOutputDtoV2 cuisineOutputDtoV2 = createModelWithId(cuisine.getId(), cuisine);
        modelMapper.map(cuisine, cuisineOutputDtoV2);

        cuisineOutputDtoV2.add(buildLinks.getLinkToCuisines("cuisines"));
        //cuisineOutputDto.add(linkTo(CuisineController.class).withRel("cozinhas"));

        return cuisineOutputDtoV2;
    }
}
