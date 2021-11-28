package ca.com.rlsp.rlspfoodapi.domain.exception;


public class UserNotFoundException extends EntityNotFoundException {

    public static final String MSG_USER_IS_NOT_FOUND_DATABASE = "Client of code %d not found into the Database";

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(Long clientId) {
        this(String.format(MSG_USER_IS_NOT_FOUND_DATABASE, clientId));
    }
}