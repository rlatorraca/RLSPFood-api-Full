package ca.com.rlsp.rlspfoodapi.domain.exception;

public class PaymentTypeNotFoundException extends  EntityNotFoundException{

    public static final String MSG_PAYMENT_TYPE_IS_NOT_FOUND_DATABASET = "Payment Type of code %s doesn't exist";

    public PaymentTypeNotFoundException(String msg) {
        super(msg);
    }

    public PaymentTypeNotFoundException(Long paymentTypeId) {
        this(String.format(MSG_PAYMENT_TYPE_IS_NOT_FOUND_DATABASET,paymentTypeId));
    }
}
