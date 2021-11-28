package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("rlspfood.jwt.keystore")
public class RlspFoodJwtKeyStoreProperties {

    //@NotBlank
    //private String path;

    // Faz com que seja possivel usar 'classpath OR file"nos arquivo 'application.properties' do Spring
    @NotNull
    private Resource jksLocation;

    @NotBlank
    private String password;

    @NotBlank
    private String keyPairAlias;

}
