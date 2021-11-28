package ca.com.rlsp.rlspfoodapi.infra.service.email;

import ca.com.rlsp.rlspfoodapi.core.email.EmailProperties;
import ca.com.rlsp.rlspfoodapi.domain.service.SendEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;

@Slf4j
public class MockSmtpSendEmailService extends SmtpSendEmailService {

    @Override
    public void send(Message message) {
        String body = processTemplateFreeMarker(message);

        log.info("RLSP MOCK email to : {} \n{}", message.getDestinations(), body);
    }
}
