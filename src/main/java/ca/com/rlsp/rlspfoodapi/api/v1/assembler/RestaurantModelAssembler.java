package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.RestaurantController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.RestaurantInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOutputDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public RestaurantModelAssembler() {
        super(RestaurantController.class, RestaurantOutputDto.class);
    }


    /*
        Convert MODEL -> DTO para PUT
    */
    public RestaurantOutputDto fromControllerToOutput(Restaurant restaurant) {
        /*
        CuisineOutputDTO cuisineDTO = new CuisineOutputDTO();
        cuisineDTO.setId(restaurant.getCuisine().getId());
        cuisineDTO.setName(restaurant.getCuisine().getName());

        ProvinceOutputDTO provinceDTO = new ProvinceOutputDTO();
        provinceDTO.setName(restaurant.getAddress().getCity().getProvince().getName());

        CityOutputDTO cityDTO = new CityOutputDTO();
        cityDTO.setName(restaurant.getAddress().getCity().getName());
        cityDTO.setProvince(provinceDTO);

        AddressOutputDTO addressDTO = new AddressOutputDTO();
        addressDTO.setCity(cityDTO);
        addressDTO.setComplement(restaurant.getAddress().getComplement());
        addressDTO.setDistrict(restaurant.getAddress().getDistrict());
        addressDTO.setNumber(restaurant.getAddress().getComplement());
        addressDTO.setPostalcode(restaurant.getAddress().getPostalcode());
        addressDTO.setStreet(restaurant.getAddress().getStreet());


        RestaurantOutputDTO restaurantDTO = new RestaurantOutputDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDeliveryFee(restaurant.getDeliveryFee());
        restaurantDTO.setCuisine(cuisineDTO);
        restaurantDTO.setAddress(addressDTO);
        restaurantDTO.setCreatedDate(restaurant.getCreatedDate());
        restaurantDTO.setDateLastUpdate(restaurant.getDateLastUpdate());

        return restaurantDTO;
         */

        return modelMapper.map(restaurant, RestaurantOutputDto.class); // Mas o mapeamento substituindo o codigo acima
    }


    /*
        Convert MODEL -> DTO (para POST)
    */
    public RestaurantOutputDto fromControllerToOutputPost(Restaurant restaurant) {
        /*
        CuisineOutputDTO cuisineDTO = new CuisineOutputDTO();
        cuisineDTO.setId(restaurant.getCuisine().getId());
        cuisineDTO.setName(restaurant.getCuisine().getName());


        RestaurantOutputDTO restaurantDTO = new RestaurantOutputDTO();
        restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDeliveryFee(restaurant.getDeliveryFee());
        restaurantDTO.setCuisine(cuisineDTO);
        restaurantDTO.setCreatedDate(restaurant.getCreatedDate());
        restaurantDTO.setDateLastUpdate(restaurant.getDateLastUpdate());

        return restaurantDTO;
         */
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
        //cuisineDTO.setId(restaurant.getCuisine().getId());
        cuisineDTO.setName(restaurant.getCuisine().getName());

        RestaurantInputDto restaurantDTO = new RestaurantInputDto();
        //restaurantDTO.setId(restaurant.getId());
        restaurantDTO.setName(restaurant.getName());
        restaurantDTO.setDeliveryFee(restaurant.getDeliveryFee());
        restaurantDTO.setCuisine(cuisineDTO);
        return restaurantDTO;
    }


    /*
        Convert MODEL -> DTO (list GET)
    */
    public List<RestaurantOutputDto> fromControllerToOutputList(List<Restaurant> restaurants){
        return restaurants.stream()
                .map(restaurant -> fromControllerToOutput(restaurant))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantOutputDto toModel(Restaurant restaurant) {
        RestaurantOutputDto restaurantOutputDto = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantOutputDto);

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            restaurantOutputDto.add(buildLinks.getLinkToRestaurants("restaurants"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            restaurantOutputDto.add(buildLinks.getLinkToPaymentTypeOnRestaurants(restaurant.getId(),
                    "payment-types"));
        }

        if(rlspFoodSecurity.hasPermissionToManageRestaurantRegister()){
            restaurantOutputDto.add(buildLinks.getLinkRestaurantManagers(restaurant.getId(),
                    "managers"));
        }

        if(rlspFoodSecurity.hasPermissionToManageOpenCloseRestaurants(restaurant.getId())) {
            if (restaurant.activationPermitted()) {
                restaurantOutputDto.add(
                        buildLinks.getLinkToActiveRestaurant(restaurant.getId(), "active"));
            }

            if (restaurant.inactivationPermitted()) {
                restaurantOutputDto.add(
                        buildLinks.getLinkToInactiveRestaurant(restaurant.getId(), "inactive"));
            }
        }


        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            if (restaurant.openPermitted()) {
                restaurantOutputDto.add(
                        buildLinks.getLinkToOpeningRestaurant(restaurant.getId(), "open"));
            }

            if (restaurant.closePermitted()) {
                restaurantOutputDto.add(
                        buildLinks.getLinkToClosingRestaurant(restaurant.getId(), "close"));
            }
        }

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            restaurantOutputDto.add(buildLinks.getLinkToProducts(restaurant.getId(), "products"));
        }



        if(rlspFoodSecurity.hasPermissionToQueryCuisines()) {
            //restaurantOutputDto.getCuisine().add(buildLinks.getLinkToCuisines());
            restaurantOutputDto.getCuisine().add(buildLinks.getLinkToCuisine(restaurant.getCuisine().getId()));
        }


        if (restaurantOutputDto.getAddress() != null
                && restaurantOutputDto.getAddress().getCity() != null) {
            restaurantOutputDto.getAddress().getCity().add(
                    buildLinks.getLinkToCities(restaurant.getAddress().getCity().getId()));
        }


        return restaurantOutputDto;
    }
}
