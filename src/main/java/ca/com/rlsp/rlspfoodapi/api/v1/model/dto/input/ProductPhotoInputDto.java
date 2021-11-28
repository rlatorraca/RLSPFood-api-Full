package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;


import ca.com.rlsp.rlspfoodapi.core.validation.FileContentType;
import ca.com.rlsp.rlspfoodapi.core.validation.PhotoSize;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPhotoInputDto {

    @ApiModelProperty(hidden = true)
    @NotNull
    @PhotoSize(max="500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE} )
    private MultipartFile file;

    //private String fileName;
    @ApiModelProperty(value = "Product photo description", required = true)
    @NotBlank
    private String description;
    //private String contentType;
    //private Long size;

    //private Product product;
}
