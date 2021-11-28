package ca.com.rlsp.rlspfoodapi.core.email;

import ca.com.rlsp.rlspfoodapi.domain.service.SendEmailService;
import ca.com.rlsp.rlspfoodapi.infra.service.email.MockSmtpSendEmailService;
import ca.com.rlsp.rlspfoodapi.infra.service.email.SandboxSmtpSendEmailService;
import ca.com.rlsp.rlspfoodapi.infra.service.email.SmtpSendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public SendEmailService sendEmailService() {

        switch(emailProperties.getImplementation()) {
            case MOCK : return new MockSmtpSendEmailService();
            case SMTP : return new SmtpSendEmailService();
            case SANDBOX: return new SandboxSmtpSendEmailService();
            default: return null;
        }
    }
}
