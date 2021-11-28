package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.OrderController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.OrderShortOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class OrderShortModelAssembler extends RepresentationModelAssemblerSupport<Order, OrderShortOutputDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public OrderShortModelAssembler() {
        super(OrderController.class, OrderShortOutputDto.class);
    }


    /*
        Convert MODEL -> DTO para PUT
    */
    public OrderShortOutputDto fromControllerToOutput(Order order) {

        return modelMapper.map(order, OrderShortOutputDto.class); // Mas o mapeamento substituindo o codigo acima
    }



    /*
        Convert MODEL -> DTO (list GET)
    */
    public List<OrderShortOutputDto> fromControllerToOutputList(Collection<Order> orders){
        return orders.stream()
                .map(order -> fromControllerToOutput(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderShortOutputDto toModel(Order order) {
        OrderShortOutputDto orderShortOutputDto = createModelWithId(order.getId(), order);
        modelMapper.map(order, orderShortOutputDto);


        //orderShortOutputDto.add(linkTo(OrderController.class).withRel("orders_short"));

        if(rlspFoodSecurity.hasPermissionToQueryOrders()){
            orderShortOutputDto.add(buildLinks.getLinkToOrders("orders-short"));
        }

//        orderShortOutputDto.getRestaurant().add(linkTo(methodOn(RestaurantController.class)
//                .findById(order.getRestaurant().getId())).withSelfRel());

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()){
            orderShortOutputDto.getRestaurant().add(
                    buildLinks.getLinkToRestaurant(order.getRestaurant().getId()));
        }


//        orderShortOutputDto.getUser().add(linkTo(methodOn(UserController.class)
//                .findById(order.getUser().getId())).withSelfRel());

        if(rlspFoodSecurity.hasPermissionToQueryUsersGroupsPermissions()){
            orderShortOutputDto.getUser()
                    .add(buildLinks.getLinkToUser(order.getUser().getId()));
        }

        return orderShortOutputDto;

    }
}
