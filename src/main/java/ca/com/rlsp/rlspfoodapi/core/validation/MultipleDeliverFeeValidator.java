package ca.com.rlsp.rlspfoodapi.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class MultipleDeliverFeeValidator implements ConstraintValidator<MulitpleDeliveryFeeAnnotation, Number> {

    private int numberMultiple;

    @Override
    public void initialize(MulitpleDeliveryFeeAnnotation constraintAnnotation) {
        this.numberMultiple = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean isValid = false;

        if(value != null){
            var valuePassedByUser = BigDecimal.valueOf(value.doubleValue());
            var multipleBase = BigDecimal.valueOf(numberMultiple);
            var remainderIsZero = valuePassedByUser.remainder(multipleBase);
            isValid = (remainderIsZero.ZERO.compareTo(remainderIsZero) == 0 );

        }

        return isValid;
    }
}
