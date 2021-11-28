package ca.com.rlsp.rlspfoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntityIsForeignKeyException extends GenericBusinessException{

    // Quando informamos o HttpStatus qualquer
    public EntityIsForeignKeyException(HttpStatus status, String msg) {
        super(status, msg);
    }

    // Caso nao facemos o HttpStatus o padrao sera o HttpStatus.NOTFOUND
    public EntityIsForeignKeyException(String msg){
        this(HttpStatus.NOT_FOUND, msg);
    }
}
