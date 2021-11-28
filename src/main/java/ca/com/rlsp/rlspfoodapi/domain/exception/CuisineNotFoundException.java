package ca.com.rlsp.rlspfoodapi.domain.exception;


public class CuisineNotFoundException extends EntityNotFoundException {

    public static final String MSG_CUISINE_IS_NOT_FOUND_DATABASE = "Cuisine of code %d not found into the Database";

    public CuisineNotFoundException(String msg){
        super(msg);
    }

    public CuisineNotFoundException(Long cuisineId) {
        this(String.format(MSG_CUISINE_IS_NOT_FOUND_DATABASE, cuisineId));
    }
}