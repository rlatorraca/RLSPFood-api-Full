package ca.com.rlsp.rlspfoodapi.core.validation;

import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Locale;

public class ValueZeroInsertDescriptionValidator  implements ConstraintValidator<ValueZeroInsertDescription, Object> {

    private String valueField;

    private String toCheckField;

    private String mandatoryDescription;

    @Override
    public void initialize(ValueZeroInsertDescription constraintAnnotation) {
       this.valueField = constraintAnnotation.valueField();
       this.toCheckField = constraintAnnotation.toCheckField();
       this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
    }


    @Override
    public boolean isValid(Object objectToValidation, ConstraintValidatorContext context) {
        boolean isValid = true;

        try {
            BigDecimal valueToCheck = (BigDecimal) BeanUtils.getPropertyDescriptor(objectToValidation.getClass(), valueField)
                    .getReadMethod().invoke(objectToValidation);

            String fieldDescription = (String) BeanUtils.getPropertyDescriptor(objectToValidation.getClass(), toCheckField)
                    .getReadMethod().invoke(objectToValidation);

            if(valueToCheck != null && BigDecimal.ZERO.compareTo(valueToCheck) == 0 && fieldDescription != null){
                isValid = fieldDescription.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
            }

            return isValid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
