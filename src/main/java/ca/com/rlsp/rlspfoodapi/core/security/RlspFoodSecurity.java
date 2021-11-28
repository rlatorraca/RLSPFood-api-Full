package ca.com.rlsp.rlspfoodapi.core.security;

import ca.com.rlsp.rlspfoodapi.domain.repository.OrderRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;


@Component
public class RlspFoodSecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Authentication getAuthentication() {
        // Pega o Contexto atual de seguranca e um objeto do Token que representa a autenticaca atual
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal(); // Retorna um instancia de JWT (do usuario autenticado)

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId){
        return restaurantRepository.restaurantHasManager(restaurantId, getUserId());
    }

    public boolean manageRestaurantOrders(String orderCode) {
        return orderRepository.isOrderManagedFor(orderCode, getUserId());
    }


    /**
     * Check if an Authenticated user is the same that requested the action
     * @param userId
     * @return
     */
    public boolean userAuthenticatedAndEqualUserPassed(Long userId){
        return getUserId() != null && userId != null && getUserId() == userId;
    }

    /**
     * Verify the authorities existing for that user
     * @param authorityName
     * @return
     */
    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    /**
     * Used to know if user has permission to modify status of an order
     * @param orderCode
     * @return
     */
    public boolean hasPermissionToModifyOrderStatus(String orderCode){
        return hasAuthority("SCOPE_WRITE")
                && (
                hasAuthority("EDIT_ORDERS") || manageRestaurantOrders(orderCode));
    }

    /**
     * Check if user is Authenticated
     * @return
     */
    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    /**
     * Check if user has SCOPE_WRITE
     * @return
     */
    public boolean hasWriteScope() {
        return hasAuthority("SCOPE_WRITE");
    }

    /**
     * Check if user has SCOPE_READ
     * @return
     */
    public boolean hasReadScope() {
        return hasAuthority("SCOPE_READ");
    }

    /**
     * Methods to check the scope and permissions
     * @return
     */
    public boolean hasPermissionToQueryRestaurants() {
        return hasReadScope() && isAuthenticated();
    }

    public boolean hasPermissionToManageRestaurantRegister () {
        return hasWriteScope() && hasAuthority("EDIT_RESTAURANTS");
    }

    public boolean hasPermissionToManageOpenCloseRestaurants (Long restaurantId) {
        return hasWriteScope() && (hasAuthority("EDITAR_RESTAURANTES")
                || manageRestaurant(restaurantId));
    }

    public boolean hasPermissionToQueryUsersGroupsPermissions () {
        return hasReadScope() && hasAuthority("QUERY_USERS_GROUPS_PERMISSIONS");
    }

    public boolean hasPermissionToEditUsersGroupsPermissions() {
        return hasWriteScope() && hasAuthority("EDIT_USERS_GROUPS_PERMISSIONS");
    }

    public boolean hasPermissionToQueryOrders(Long clienteId, Long restauranteId) {
        return hasReadScope() && (hasAuthority("QUERY_ORDERS")
                || userAuthenticatedAndEqualUserPassed(clienteId)
                || manageRestaurant(restauranteId));
    }

    public boolean hasPermissionToQueryOrders() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean hasPermissionToQueryPaymentTypes() {
        return isAuthenticated() && hasReadScope();
    }

    public boolean hasPermissionToQueryCities () {
        return isAuthenticated() && hasReadScope();
    }

    public boolean hasPermissionToQueryProvinces () {
        return isAuthenticated() && hasWriteScope();
    }

    public boolean hasPermissionToQueryCuisines () {
        return isAuthenticated() && hasReadScope();
    }

    public boolean hasPermissionToQueryStatistics () {
        return hasReadScope() && hasAuthority("GENERATE_STATISTICS");
    }

}
