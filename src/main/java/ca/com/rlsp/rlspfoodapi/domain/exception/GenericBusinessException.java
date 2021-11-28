package ca.com.rlsp.rlspfoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GenericBusinessException extends ResponseStatusException {

//    public GenericBusinessException(String msg){
//        super(msg);
//    }
//
//    public GenericBusinessException(String msg, Throwable cause){
//        super(msg, cause);
//    }

    public GenericBusinessException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public GenericBusinessException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public GenericBusinessException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public GenericBusinessException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }

}