package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.RestaurantController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantBasicsOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBasicsModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicsOutputDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public RestaurantBasicsModelAssembler() {
        super(RestaurantController.class, RestaurantBasicsOutputDto.class);
    }


    @Override
    public RestaurantBasicsOutputDto toModel(Restaurant restaurant) {
        RestaurantBasicsOutputDto restaurantBasicsOutputDto = createModelWithId(restaurant.getId(),restaurant);
        modelMapper.map(restaurant, restaurantBasicsOutputDto);

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            restaurantBasicsOutputDto.add(buildLinks.getLinkToRestaurants("restaurants"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryCuisines()) {
            //restaurantOutputDto.getCuisine().add(buildLinks.getLinkToCuisines());
            restaurantBasicsOutputDto.getCuisine().add(buildLinks.getLinkToCuisine(restaurant.getCuisine().getId()));
        }

        return restaurantBasicsOutputDto;
    }

    @Override
    public CollectionModel<RestaurantBasicsOutputDto> toCollectionModel(Iterable<? extends Restaurant> restaurants) {
        CollectionModel<RestaurantBasicsOutputDto> collectionModel = super.toCollectionModel(restaurants);

        if (rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            collectionModel.add(buildLinks.getLinkToRestaurants());
        }

        return collectionModel;
    }
}
