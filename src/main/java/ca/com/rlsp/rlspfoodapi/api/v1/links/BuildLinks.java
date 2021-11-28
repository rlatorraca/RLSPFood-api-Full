package ca.com.rlsp.rlspfoodapi.api.v1.links;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.*;
import ca.com.rlsp.rlspfoodapi.api.v1.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuildLinks {

    public static final TemplateVariables VARIABLES_PAGINATION =  new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)

    );

    public static final TemplateVariables VARIABLES_FILTER = new TemplateVariables(
            new TemplateVariable("userId", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("startDate", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("endDate", TemplateVariable.VariableType.REQUEST_PARAM)

    );

    public static final TemplateVariables VARIABLES_STATISTICS = new TemplateVariables(
            new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("startDate", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("endDate", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("timeOffSet", TemplateVariable.VariableType.REQUEST_PARAM)

    );

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projection", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link getLinkToOrders(String rel) {
        String orderURI = linkTo(methodOn(OrderController.class).searchByFilterPageable(null,
                null)).toUri().toString();

        return Link.of(UriTemplate.of(orderURI, VARIABLES_PAGINATION.concat(VARIABLES_FILTER)),rel);
    }

    public Link getLinkToRestaurants(String rel) {
        String restaurantURI = linkTo(RestaurantController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantURI, PROJECTION_VARIABLES), rel);
    }

    public Link getLinkToRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .findById(restaurantId)).withRel(rel);
    }

    public Link getLinkToRestaurant(Long restaurantId) {
        return getLinkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToUser(Long orderId,  String rel) {
       return linkTo(methodOn(UserController.class)
                .findById(orderId)).withRel(rel);
    }

    public Link getLinkToUser(Long orderId) {
        return getLinkToUser(orderId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToUsers() {
        return getLinkToUsers(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToUsers(String rel) {
        return linkTo(UserController.class).withRel(rel);
    }



    public Link getLinkToAddressDelivery(Long cityId,  String rel) {
        return linkTo(methodOn(CityController.class)
                .findById(cityId)).withRel(rel);
    }

    public Link getLinkToAddressDelivery(Long cityId) {
        return getLinkToAddressDelivery(cityId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToOrderItems(Long restaurantId,  Long productId, String rel) {

        return linkTo(methodOn(RestaurantProductController.class)
                .findByRestaurantIdAndByProductId(restaurantId , productId))
                .withRel(rel);
    }

    public Link getLinkToOrderItems(Long restaurantId,  Long productId) {
        return getLinkToOrderItems(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToUserGroups(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .listAll(userId)).withRel(rel);
    }

    public Link getLinkToUserGroups(Long userId) {
        return getLinkToUserGroups(userId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkRestaurantManagers(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserManagerController.class)
                .listOne(restaurantId)).withRel(rel);
    }

    public Link getLinkRestaurantManagers(Long restaurantId) {
        return getLinkRestaurantManagers(restaurantId, IanaLinkRelations.SELF.value());
    }


    public Link getLinkToPaymentTypeOrder(Long orderId, String rel) {
        return linkTo(methodOn(PaymentTypeController.class)
                .findById(orderId, null)).withRel(rel);
    }

    public Link getLinkToPaymentTypeOrder(Long paymentTypeId) {
        return getLinkToPaymentType(paymentTypeId, IanaLinkRelations.SELF.value());
    }


    public Link getLinkToPaymentType(Long paymentTypeId, String rel) {
        return linkTo(methodOn(PaymentTypeController.class)
                .findById(paymentTypeId, null)).withRel(rel);
    }

    public Link getLinkToPaymentType(Long paymentTypeId) {
        return getLinkToPaymentType(paymentTypeId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToPaymentType(String rel) {
        return linkTo(PaymentTypeController.class).withRel(rel);
    }

    public Link getLinkToPaymentType() {
        return getLinkToPaymentType(IanaLinkRelations.SELF.value());
    }


    public Link getLinkToCities(Long cityId, String rel) {
        return linkTo(methodOn(CityController.class)
                .findById(cityId)).withRel(rel);
    }

    public Link getLinkToCities(Long cityId) {
        return getLinkToCities(cityId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCities(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link getLinkToCities() {
        return getLinkToCities(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToProvince(Long provinceId, String rel) {
        return linkTo(methodOn(ProvinceController.class)
                .findById(provinceId)).withRel(rel);
    }

    public Link getLinkToProvince(Long provinceId) {
        return getLinkToProvince(provinceId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToProvinces(String rel) {
        return linkTo(ProvinceController.class).withRel(rel);
    }

    public Link getLinkToProvinces() {
        return getLinkToProvinces(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCuisines(String rel) {
        return linkTo(CuisineController.class).withRel(rel);
    }

    public Link getLinkToCuisines() {
        return getLinkToCuisines(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCreateAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).create(orderCode)).withRel(rel);
    }
    public Link getLinkToConfirmAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).confirm(orderCode)).withRel(rel);
    }

    public Link getLinkToStartAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).start(orderCode)).withRel(rel);
    }

    public Link getLinkToCancelAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).cancel(orderCode)).withRel(rel);
    }

    public Link getLinkToOvenAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).oven(orderCode)).withRel(rel);
    }

    public Link getLinkToRoadAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).road(orderCode)).withRel(rel);
    }

    public Link getLinkToReadyAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).ready(orderCode)).withRel(rel);
    }

    public Link getLinkToDeliveryAnOrder(String orderCode, String rel) {
        return linkTo(methodOn(StatusOrderController.class).deliver(orderCode)).withRel(rel);
    }

//    public Link getLinkToRestaurants(String rel) {
//        return linkTo(RestaurantController.class).withRel(rel);
//    }

    public Link getLinkToRestaurants() {
        return getLinkToRestaurants(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToPaymentTypeOnRestaurantDetach(Long restaurantId, Long paymentTypeId, String rel) {
        return linkTo(methodOn(RestaurantPaymentTypeController.class)
                .detachPaymentType(restaurantId, paymentTypeId))
                .withRel(rel);
    }

    public Link getLinkToPaymentTypeOnRestaurantAttach(Long restauranteId, String rel) {
        return linkTo(methodOn(RestaurantPaymentTypeController.class)
                .attachPaymentType(restauranteId, null))
                .withRel(rel);
    }

    public Link getLinkToPaymentTypeOnRestaurants(Long restauranteId, String rel) {
        return linkTo(methodOn(RestaurantPaymentTypeController.class)
                .listAllByRestaurantId(restauranteId)).withRel(rel);
    }

    public Link getLinkToManagerRestaurantDetach(Long restaurantId, Long userId, String rel) {

        return linkTo(methodOn(RestaurantUserManagerController.class)
                .detachManager(restaurantId, userId)).withRel(rel);
    }

    public Link getLinkToManagerRestaurantAttach(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserManagerController.class)
                .attachManager(restaurantId, null)).withRel(rel);
    }

    public Link getLinkToPaymentTypeOnRestaurants(Long restaurantId) {
        return getLinkToPaymentTypeOnRestaurants(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCuisine(Long cozinhaId, String rel) {
        return linkTo(methodOn(CuisineController.class)
                .findBy1Id(cozinhaId)).withRel(rel);
    }

    public Link getLinkToCuisine(Long cozinhaId) {
        return getLinkToCuisine(cozinhaId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToOpeningRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .openRestaurant(restaurantId)).withRel(rel);
    }

    public Link getLinkToClosingRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .closeRestaurant(restaurantId)).withRel(rel);
    }

    public Link getLinkToInactiveRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .inactivate(restaurantId)).withRel(rel);
    }

    public Link getLinkToActiveRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .activate(restaurantId)).withRel(rel);
    }


    // Products of a Restaurant
    public Link getLinkToProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .listAllActives(restaurantId, null))
                .withRel(rel);
    }

    public Link getLinkToProducts(Long restaurantId) {
        return getLinkToProducts(restaurantId, IanaLinkRelations.SELF.value());
    }


    // Product Photos
    public Link getLinkToPhotoProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductPhotoController.class)
                .findProductPhoto(restaurantId, productId)).withRel(rel);
    }

    public Link getLinkToPhotoProduct(Long restaurantId, Long productId) {
        return getLinkToPhotoProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToGroups(String rel) {
        return linkTo(GroupController.class).withRel(rel);
    }

    public Link getLinkToGroups() {
        return getLinkToGroups(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToGroupPermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .listAll(groupId)).withRel(rel);
    }

    public Link getLinkToGroupPermissions(Long groupId) {
        return getLinkToGroupPermissions(groupId, IanaLinkRelations.SELF.value());
    }
    public Link getLinkToPermissions(String rel) {
        return linkTo(PermissionController.class).withRel(rel);
    }

    public Link getLinkToPermissions() {
        return getLinkToPermissions(IanaLinkRelations.SELF.value());   }

    public Link getLinkToPermissionsAttach(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .attach(groupId, null)).withRel(rel);
    }

    public Link getLinkToPermissionsDetach(Long groupId, Long permissionId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .detach(groupId, permissionId)).withRel(rel);
    }

    public Link getLinkToGroupAttach(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .attachGroup(userId, null)).withRel(rel);
    }

    public Link getLinkToUserGroupDetach(Long userId, Long groupId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .detachGroup(userId, groupId)).withRel(rel);
    }

    public Link getLinkToStatistics(String rel) {
        return linkTo(StatisticsController.class).withRel(rel);
    }

    public Link getLinkToStatisticsDailySales(String rel) {

        String dailySalesURI = linkTo(methodOn(StatisticsController.class)
                .queryDailySalesJSON(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(dailySalesURI, VARIABLES_STATISTICS), rel);
    }


}
