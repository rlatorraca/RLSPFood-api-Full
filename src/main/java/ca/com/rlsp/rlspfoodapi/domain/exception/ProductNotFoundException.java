package ca.com.rlsp.rlspfoodapi.domain.exception;


public class ProductNotFoundException extends EntityNotFoundException {

    public static final String MSG_PRODUCT_IS_NOT_FOUND_DATABASE = "Product of code %d for restaurant of code %d not found into the Database";

    public ProductNotFoundException(String msg){
        super(msg);
    }

    public ProductNotFoundException(Long restaurantId, Long productId ) {
        this(String.format(MSG_PRODUCT_IS_NOT_FOUND_DATABASE, productId, restaurantId));
    }
}