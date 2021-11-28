package ca.com.rlsp.rlspfoodapi.domain.exception;


public class CityNotFoundException extends EntityNotFoundException {

    public static final String MSG_CITY_IS_NOT_FOUND_DATABASE = "City of code %d not found into the Database";

    public CityNotFoundException(String msg){
        super(msg);
    }

    public CityNotFoundException(Long cityId) {
        this(String.format(MSG_CITY_IS_NOT_FOUND_DATABASE, cityId));
    }
}