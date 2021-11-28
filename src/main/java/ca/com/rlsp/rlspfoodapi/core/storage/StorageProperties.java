package ca.com.rlsp.rlspfoodapi.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties("rlspfood.storage")
@Getter
@Setter
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();
    private StorageType storageType = StorageType.LOCAL;

    @Getter
    @Setter
    public class Local {
        private Path photosDirectory;
    }

    @Getter
    @Setter
    public class S3 {
        private String accessKey;
        private String password;
        private String bucket;
        private String photosDirectory;
        private Regions region;
    }
}