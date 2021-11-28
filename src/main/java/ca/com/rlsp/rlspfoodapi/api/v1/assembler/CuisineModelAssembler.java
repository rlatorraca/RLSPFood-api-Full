package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.CuisineController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.RestaurantInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
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
public class CuisineModelAssembler extends RepresentationModelAssemblerSupport<Cuisine, CuisineOutputDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public CuisineModelAssembler() {
        super(CuisineController.class, CuisineOutputDto.class);
    }


    /*
        Convert MODEL -> DTO para PUT
    */
    public CuisineOutputDto fromControllerToOutput(Cuisine cuisine) {

        return modelMapper.map(cuisine, CuisineOutputDto.class); // Mas o mapeamento substituindo o codigo acima
    }


    /*
        Convert MODEL -> DTO (para POST)
    */
    public RestaurantOutputDto fromControllerToOutputPost(Restaurant restaurant) {

        return modelMapper.map(restaurant, RestaurantOutputDto.class); // Mas o mapeamento substituindo o codigo acima
    }

    /*
        Convert MODEL -> DTO
    */
    public RestaurantInputDto fromControllerToInput(Restaurant restaurant) {
        System.out.println(restaurant.getCuisine().getId());
        System.out.println(restaurant.getDeliveryFee());
        System.out.println(restaurant.getName());

        CuisineInputDto cuisineDTO = new CuisineInputDto();
        cuisineDTO.setId(restaurant.getCuisine().getId());
        cuisineDTO.setName(restaurant.getCuisine().getName());

        RestaurantInputDto restaurantDTO = new RestaurantInputDto();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDeliveryFee(restaurant.getDeliveryFee());
        restaurantDTO.setCuisine(cuisineDTO);
        return restaurantDTO;
    }


    /*
        Convert MODEL -> DTO (list GET)
    */
    public List<CuisineOutputDto> fromControllerToOutputList(List<Cuisine> cuisines){
        return cuisines.stream()
                .map(cuisine -> fromControllerToOutput(cuisine))
                .collect(Collectors.toList());
    }

    @Override
    public CollectionModel<CuisineOutputDto> toCollectionModel(Iterable<? extends Cuisine> cuisine) {
        CollectionModel<CuisineOutputDto> collectionModel = super.toCollectionModel(cuisine);

        if(rlspFoodSecurity.hasPermissionToQueryCuisines()){
            collectionModel.add(buildLinks.getLinkToCuisines());
        }

        return collectionModel;
        //return super.toCollectionModel(cuisine).add(buildLinks.getLinkToCuisines());
    }

    @Override
    public CuisineOutputDto toModel(Cuisine cuisine) {

        /* Usa o RepresentationModelAssemblerSupport<> que ja implementa _self link*/
        CuisineOutputDto cuisineOutputDto = createModelWithId(cuisine.getId(), cuisine);
        modelMapper.map(cuisine, cuisineOutputDto);

        if(rlspFoodSecurity.hasPermissionToQueryCuisines()){
            cuisineOutputDto.add(buildLinks.getLinkToCuisines("cuisines"));
            //cuisineOutputDto.add(linkTo(CuisineController.class).withRel("cozinhas"));
        }

        return cuisineOutputDto;
    }
}
