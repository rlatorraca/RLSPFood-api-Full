package ca.com.rlsp.rlspfoodapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("rlspfood.email")
public class EmailProperties {

    @NotNull
    private String sender;


    /*
         Atribuimos MOCK e o Standard
         Nao  enviar e-mails de verdade caso você esqueça de definir a propriedade
     */
    private Implementation implementation = Implementation.MOCK;

    private Sandbox sandbox = new Sandbox();

    public enum Implementation {
        SMTP, MOCK, SANDBOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String emailTo;
    }
}
