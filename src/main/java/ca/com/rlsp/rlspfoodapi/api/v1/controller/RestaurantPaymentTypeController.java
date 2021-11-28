package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.PaymentTypeModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PaymentTypeOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.RestaurantPaymentTypeControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.RestaurantRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path="/restaurants/{restaurantId}/paymenttype",  produces = {MediaType.APPLICATION_JSON_VALUE})
@RequestMapping(path = "/v1/restaurants/{restaurantId}/paymenttype",  produces = {MediaType.APPLICATION_JSON_VALUE})
public class RestaurantPaymentTypeController implements RestaurantPaymentTypeControllerOpenApi {


    private RestaurantRegistrationService restaurantRegistrationService;
    private RestaurantRepository restaurantRepository;
    private PaymentTypeModelAssembler paymentTypeModelAssembler;
    private BuildLinks buildLinks;
    private RlspFoodSecurity rlspFoodSecurity;

    public RestaurantPaymentTypeController(RestaurantRegistrationService restaurantRegistrationService,
                                           RestaurantRepository restaurantRepository,
                                           PaymentTypeModelAssembler paymentTypeModelAssembler,
                                           BuildLinks buildLinks,
                                           RlspFoodSecurity rlspFoodSecurity) {
        this.restaurantRegistrationService = restaurantRegistrationService;
        this.restaurantRepository = restaurantRepository;
        this.paymentTypeModelAssembler = paymentTypeModelAssembler;
        this.buildLinks = buildLinks;
        this.rlspFoodSecurity = rlspFoodSecurity;
    }

    @CheckSecurity.Restaurant.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping
    //public List<PaymentTypeOutputDto> listAllByRestaurantId(@PathVariable("restaurantId")
    public CollectionModel<PaymentTypeOutputDto> listAllByRestaurantId(@PathVariable("restaurantId")
                                                          Long id) {
        Restaurant restaurant = restaurantRegistrationService.findOrFail(id);
        //List<PaymentTypeOutputDto> paymentTypeOutputDtoList = paymentTypeModelAssembler7.toCollectionModel(restaurant.getPaymentTypeList());

        CollectionModel<PaymentTypeOutputDto> paymentTypeOutputDtoList =
                paymentTypeModelAssembler.toCollectionModel(restaurant.getPaymentTypeList())
                        .removeLinks();

        paymentTypeOutputDtoList.add(buildLinks.getLinkToPaymentTypeOnRestaurants(id));

        if(rlspFoodSecurity.hasPermissionToManageOpenCloseRestaurants(id)) {
            paymentTypeOutputDtoList.add(buildLinks.getLinkToPaymentTypeOnRestaurantAttach(id,"attach"));


            // Links for DETACH & ATTACH in Restaurant PaymentType
            paymentTypeOutputDtoList.getContent().forEach( paymentTypeOutput ->{
                        paymentTypeOutput
                                .add(buildLinks.getLinkToPaymentTypeOnRestaurantDetach(
                                        id,
                                        paymentTypeOutput.getId(),
                                        "detach"));
                    }
            );
        }

        return paymentTypeOutputDtoList;

    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant
    @DeleteMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detachPaymentType(@PathVariable("restaurantId") Long restaurantId, @PathVariable("paymentTypeId") Long paymentTypeId){
        restaurantRegistrationService.detachPaymentType(restaurantId,paymentTypeId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PutMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attachPaymentType(@PathVariable("restaurantId") Long restaurantId, @PathVariable("paymentTypeId") Long paymentTypeId){
        restaurantRegistrationService.attachPaymentType(restaurantId,paymentTypeId);

        return ResponseEntity.noContent().build();
    }


}
