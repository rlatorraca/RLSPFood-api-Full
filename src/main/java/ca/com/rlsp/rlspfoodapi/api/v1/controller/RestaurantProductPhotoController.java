package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.ProductPhotoModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductPhotoInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProductPhotoOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.RestaurantProductPhotoControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import ca.com.rlsp.rlspfoodapi.domain.model.ProductPhoto;
import ca.com.rlsp.rlspfoodapi.domain.service.CatalogueProductPhotoService;
import ca.com.rlsp.rlspfoodapi.domain.service.PhotoStorageService.RetrievePhoto;
import ca.com.rlsp.rlspfoodapi.domain.service.PhotoStorageService;
import ca.com.rlsp.rlspfoodapi.domain.service.ProductRegistrationService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
//@RequestMapping(path = "/restaurants/{restaurantId}/products/{productId}/photo",
//        produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products/{productId}/photo",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

    @Autowired
    private ProductRegistrationService productRegistrationService;

    @Autowired
    private CatalogueProductPhotoService catalogueProductPhotoService;

    @Autowired
    private PhotoStorageService photoStorageService;



    @Autowired
    private ProductPhotoModelAssembler productPhotoModelAssembler;

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_RESTAURANT
    @Override
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Long restaurantId,
                            @PathVariable Long productId) {
        catalogueProductPhotoService.remove(restaurantId,productId);
    }

    @CheckSecurity.Restaurant.hasPermissionToQuery// So pode acessar o metodo se tive permissao de QUERY_RESTAURANT
    @Override
    @GetMapping
    //@GetMapping
    public ProductPhotoOutputDto findProductPhoto(@PathVariable Long restaurantId,
                                                  @PathVariable Long productId) {
        ProductPhoto productPhoto = catalogueProductPhotoService
                .findAndFail(restaurantId, productId);

        return productPhotoModelAssembler.toModel(productPhoto);
        //return productPhotoModelAssembler.fromControllerToOutput(productPhoto);
    }

    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> getProductPhoto(@PathVariable Long restaurantId,
                                          @PathVariable Long productId,
                                          @RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            ProductPhoto productPhoto = catalogueProductPhotoService
                    .findAndFail(restaurantId, productId);

            // Pega o Media Type da PHOTO
            MediaType mediaTypePhoto = MediaType.parseMediaType(productPhoto.getContentType());
            // Pega a lista de Media Types aceitas pela aplicacao
            List<MediaType> mediaTypeList = MediaType.parseMediaTypes(acceptHeader);

            checkMediTypeCompatibility(mediaTypePhoto, mediaTypeList);

            RetrievePhoto retrievedPhoto = photoStorageService.retrieve(productPhoto.getFileName());

            // Verifica se esta sendo buscado a URL (na AWS S3) ou busando DIKS
            if(retrievedPhoto.hasURL()){
                return ResponseEntity
                        .status(HttpStatus.FOUND) // Foto encontrada
                        .header(HttpHeaders.LOCATION,retrievedPhoto.getUrl()) // Indica a URL que cliente seguira
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypePhoto)// Retorna o Media Type presente na Photo
                        .body(new InputStreamResource(retrievedPhoto.getInputStream()));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkMediTypeCompatibility(MediaType mediaTypePhoto, List<MediaType> mediaTypeList) throws HttpMediaTypeNotAcceptableException {

        // verifica se pelo menos 1 Media Type e compativel para a foto buscada
        boolean compatible = mediaTypeList.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypePhoto));

        if(!compatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeList);
        }
    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant
    @Override
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhotoOutputDto updatePhoto(@PathVariable Long restaurantId,
                                             @PathVariable Long productId,
                                             @RequestPart MultipartFile file,
                                             @Valid ProductPhotoInputDto photoProductInput) throws IOException {

        try{
            Product product = productRegistrationService.findOrFail(restaurantId, productId);

            //MultipartFile file = photoProductInput.getFile();

            ProductPhoto photo = new ProductPhoto();
            photo.setProduct(product);
            photo.setDescription(photoProductInput.getDescription());
            photo.setContentType(file.getContentType());
            photo.setSize(file.getSize());
            photo.setFileName(file.getOriginalFilename());

            ProductPhoto savedPhoto = catalogueProductPhotoService.savePhoto(photo, file.getInputStream());

            return productPhotoModelAssembler.fromControllerToOutput(savedPhoto);
        } catch (MaxUploadSizeExceededException | FileSizeLimitExceededException e) {
            throw new MaxUploadSizeExceededException(file.getSize());
        }

    }

    /* Old Version
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId,
                            @PathVariable Long productId,
                            @Valid ProductPhotoInput photoProductInput)  {

        String fileName = UUID.randomUUID().toString() + "_" + photoProductInput.getFile().getOriginalFilename();

        Path photoFile = Path.of("src/main/resources/photos/saved/",fileName);

        try {
            photoProductInput.getFile().transferTo(photoFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    */

}


