package ca.com.rlsp.rlspfoodapi.domain.exception;


public class PermissionNotFoundException extends EntityNotFoundException {

    public static final String MSG_PERMISSION_IS_NOT_FOUND_DATABASE = "Permission of code %d not found into the Database";

    public PermissionNotFoundException(String msg){
        super(msg);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format(MSG_PERMISSION_IS_NOT_FOUND_DATABASE, permissionId));
    }
}