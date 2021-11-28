package ca.com.rlsp.rlspfoodapi.domain.exception;


public class OrderNotFoundException extends EntityNotFoundException {

    public static final String MSG_ORDER_IS_NOT_FOUND_DATABASE = "Order of code %s not found into the Database";

    /*
    public OrderNotFoundException(String msg){
        super(msg);
    }


    //public OrderNotFoundException(Long cityId) {
        this(String.format(MSG_ORDER_IS_NOT_FOUND_DATABASE, cityId));
    }
    */

    public OrderNotFoundException(String ordercode) {
        super(String.format(MSG_ORDER_IS_NOT_FOUND_DATABASE, ordercode));
    }
}