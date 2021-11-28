package ca.com.rlsp.rlspfoodapi.domain.exception;


public class ProvinceNotFoundException extends EntityNotFoundException {

    public static final String MSG_PROVINCE_IS_NOT_FOUND_DATABASE = "Province of code %d not found into the Database";

    public ProvinceNotFoundException(String msg){
        super(msg);
    }

    public ProvinceNotFoundException(Long provinceId) {
        this(String.format(MSG_PROVINCE_IS_NOT_FOUND_DATABASE, provinceId));
    }
}