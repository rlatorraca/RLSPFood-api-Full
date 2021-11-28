package ca.com.rlsp.rlspfoodapi.domain.exception;


public class ProductPhotoNotFoundException extends EntityNotFoundException {

    public static final String MSG_PRODUCT_PHOTO_NOT_FOUND_DATABASE =
            "No Product Photo found for Restaurant of code %d amd Product of code %d";

    public ProductPhotoNotFoundException(String msg){
        super(msg);
    }

    public ProductPhotoNotFoundException(Long restaurantId, Long productId) {
        this(String.format(MSG_PRODUCT_PHOTO_NOT_FOUND_DATABASE, restaurantId, productId));
    }
}