package ca.com.rlsp.rlspfoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)// , reason="Entity not found")
public abstract class EntityNotFoundException extends GenericBusinessException {


    // Quando informamos o HttpStatus qualquer
    public EntityNotFoundException(HttpStatus status, String msg) {
        super(status, msg);
    }

    // Caso nao facemos o HttpStatus o padrao sera o HttpStatus.NOTFOUND
    public EntityNotFoundException(String msg){
        this(HttpStatus.NOT_FOUND, msg);
    }


}