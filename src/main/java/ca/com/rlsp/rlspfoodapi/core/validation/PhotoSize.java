package ca.com.rlsp.rlspfoodapi.core.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD,FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = { PhotoSizeValidator.class})
public @interface PhotoSize {

    String message() default "{PhotoSizeAnnotation}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();
}
