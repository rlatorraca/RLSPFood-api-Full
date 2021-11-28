package ca.com.rlsp.rlspfoodapi.domain.exception;

import org.springframework.http.HttpStatus;

public class GroupNotFoundException extends EntityNotFoundException{


    public static final String MSG_GROUP_IS_NOT_FOUND_DATABASE= "Group of code %d doesn't exist on database";

    public GroupNotFoundException(Long groupId) {
        this(String.format(MSG_GROUP_IS_NOT_FOUND_DATABASE, groupId));
    }


    public GroupNotFoundException(String msg) {
        super(msg);
    }
}
