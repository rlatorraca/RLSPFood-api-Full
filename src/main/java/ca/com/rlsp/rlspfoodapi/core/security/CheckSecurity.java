package ca.com.rlsp.rlspfoodapi.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Cuisine {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CUISINE')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit {}

        //@PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryCuisines()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery {}


    }

    public @interface Restaurant {

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')")
        @PreAuthorize("rlspFoodSecurity.hasPermissionToManageRestaurantRegister()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit {}

        //@PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @PreAuthorize("rlspFoodSecurity.hasPermissionToManageOpenCloseRestaurants(#restaurantId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery {}

        //@PreAuthorize("(hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')) or" +
        //        "@rlspFoodSecurity.manageRestaurant(#restaurantId)")
        @PreAuthorize("rlspFoodSecurity.hasPermissionToQueryRestaurants()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToManageRestaurant {}
    }

    public @interface Order {

        @PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') or" +
                "@rlspFoodSecurity.userAuthenticatedAndEqualUserPassed(returnObject.user.id) or" +
                "@rlspFoodSecurity.userAuthenticatedAndEqualUserPassed(returnObject.restaurant.id)" )
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToGetOneOrder {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQueryOrder { }

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @PreAuthorize("rlspFoodSecurity.hasPermissionToQueryOrders(#filter.clientId, restaurntId)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToCreateOrder {}

        @PreAuthorize("@rlspFoodSecurity.hasPermissionToModifyOrderStatus(#codeOrder)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToManagerOrders {}
    }

    public @interface PaymentType {

        @PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery {}

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_RESTAURANTS')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryPaymentTypes()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit {}
    }

    public @interface City {

        @PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery {}

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryCities()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit {}
    }

    public @interface Province {

        @PreAuthorize("hasAuthority('SCOPE_READ') and  isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery {}

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PROVINCES')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryProvinces()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit {}
    }

    public @interface UserGroup {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and  " +
                "@rlspFoodSecurity.getUserId() == #userId")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToChangeOwnPassword {}

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USERS_GROUPS') or "
                + "@rlspFoodSecurity.userAuthenticatedAndEqualUserPassed(#userId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToChangeUserData{}

        //@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_USERS_GROUPS')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToEditUsersGroupsPermissions()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToEdit { }


        //@PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('QUERY_USERS_GROUPS')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryUsersGroupsPermissions()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery { }
    }

    public @interface Statistics {

       //@PreAuthorize("hasAuthority('SCOPE_READ') and "
       //          + "hasAuthority('GENERATE_REPORTS')")
        @PreAuthorize("@rlspFoodSecurity.hasPermissionToQueryStatistics()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface hasPermissionToQuery { }

    }

}
