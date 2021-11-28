package ca.com.rlsp.rlspfoodapi;

import ca.com.rlsp.rlspfoodapi.infra.repository.customized.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) // Mostra qual sera a implementacao Padrao do SimpleJpaRepository
public class RlspfoodApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // Confgura o TimeZone para UTC e nao no GMT -3
        SpringApplication.run(RlspfoodApiApplication.class, args);
    }

}
