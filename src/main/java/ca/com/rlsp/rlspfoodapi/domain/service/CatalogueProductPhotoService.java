package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.ProductPhotoNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.ProductPhoto;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogueProductPhotoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public void remove(Long restaurantId, Long productId){
        ProductPhoto photo = findAndFail(restaurantId,productId);

        // Delete from DB
        productRepository.delete(photo);
        productRepository.flush();

        // Delete from DISK or AWS S3
        photoStorageService.remove(photo.getFileName());
    }

    @Transactional
    public ProductPhoto savePhoto(ProductPhoto productPhoto, InputStream fileData) {
        // Delete photo , if exists
        Long restaurantId = productPhoto.getRestaurantId();
        Long productId = productPhoto.getProduct().getId();
        Optional<ProductPhoto> existentPhoto = productRepository.findProductPhotoById(restaurantId, productId);
        String uuidFileName = photoStorageService.generateUUIDFileName(productPhoto.getFileName(), restaurantId, productId);
        String oldFileExistent = null;

        if(existentPhoto.isPresent()) {
            oldFileExistent = existentPhoto.get().getFileName();
            productRepository.delete(existentPhoto.get());
        }

        // Salva a foto no DB
        productPhoto.setFileName(uuidFileName); // new name using UUID
        productPhoto = productRepository.save(productPhoto);
        productRepository.flush();;

        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService
                .NewPhoto.builder()
                .newFIleName(productPhoto.getFileName())
                .contentType((productPhoto.getContentType()))
                .inputStream(fileData).build();

        // Troca ou Salva a foto no Disco Local
        photoStorageService.switchOrSave(oldFileExistent, newPhoto);
        return productPhoto;
    }

    public ProductPhoto findAndFail(Long restaurantId, Long productId) {
        return productRepository.findProductPhotoById(restaurantId, productId)
                .orElseThrow(
                        () -> new ProductPhotoNotFoundException(restaurantId, productId)
                );
    }
}
