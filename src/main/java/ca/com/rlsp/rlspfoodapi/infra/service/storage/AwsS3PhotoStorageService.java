package ca.com.rlsp.rlspfoodapi.infra.service.storage;

import ca.com.rlsp.rlspfoodapi.core.storage.StorageProperties;
import ca.com.rlsp.rlspfoodapi.domain.service.PhotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

//@Service
public class AwsS3PhotoStorageService implements PhotoStorageService {

    private static final String MSG_EXCEPTION_CONNECTION_AWS_S3 = "Cannot send File from AWS S3" ;
    private static final String MSG_EXCEPTION_REMOVE_FROM_AWS_S3 = "Cannot remove File from AWS S3" ;
    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private AmazonS3 amazonS3; // Interface from AWS S3 API
    @Override
    public void storage(NewPhoto newPhoto) {

        try {
            String filePath = getPathFile(newPhoto.getNewFIleName());

            ObjectMetadata objectMetadata = new ObjectMetadata(); // AWS Metadata
            objectMetadata.setContentType(newPhoto.getContentType());

            // Usado para preparar a Payload da chamada da API da Amazon
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata
            ).withCannedAcl(CannedAccessControlList.PublicRead); // da permissao para leitura externamente

            // Chamada da API da AWS S3
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e){
            throw new StorageException(MSG_EXCEPTION_CONNECTION_AWS_S3, e);
        }


    }

    private String getPathFile(String newFIleName) {
        return String.format("%s/%s", storageProperties.getS3().getPhotosDirectory(), newFIleName);
    }

    @Override
    public void remove(String fileName) {
        try{
            String filePath = getPathFile(fileName);
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath
            );

            amazonS3.deleteObject(deleteObjectRequest);

        } catch (Exception e){
            throw new StorageException(MSG_EXCEPTION_REMOVE_FROM_AWS_S3, e);
        }
    }

    @Override
    public RetrievePhoto retrieve(String fileName) {
        String filePath = getPathFile(fileName);

        // Monta a URL para buscar a foto denro do buket da AWS S3
        URL url = amazonS3.getUrl(
                storageProperties.getS3().getBucket(),
                filePath
        );
        return RetrievePhoto
                    .builder()
                    .url(url.toString())
                    .build();
    }

    @Override
    public String generateUUIDFileName(String orignalName, Long restaurantId, Long productId) {
        return PhotoStorageService.super.generateUUIDFileName(orignalName, restaurantId, productId);
    }

    @Override
    public void switchOrSave(String oldFileName, NewPhoto newPhoto) {
        PhotoStorageService.super.switchOrSave(oldFileName, newPhoto);
    }
}
