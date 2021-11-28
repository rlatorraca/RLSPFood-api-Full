package ca.com.rlsp.rlspfoodapi.core.validation;

import net.sf.jasperreports.data.DataFile;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhotoSizeValidator implements ConstraintValidator<PhotoSize, MultipartFile> {

    private DataSize dateSize;
    @Override
    public void initialize(PhotoSize photoSize) {
        this.dateSize = DataSize.parse(photoSize.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return  value == null || value.getSize() <= dateSize.toBytes();
    }
}
