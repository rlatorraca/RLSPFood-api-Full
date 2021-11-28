package ca.com.rlsp.rlspfoodapi.infra.service.email;

import ca.com.rlsp.rlspfoodapi.core.email.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


public class SandboxSmtpSendEmailService extends SmtpSendEmailService {

    @Autowired
    private EmailProperties emailProperties;

    @Override
    protected MimeMessage buildMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage =  super.buildMimeMessage(message);

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        mimeMessageHelper.setTo(emailProperties.getSandbox().getEmailTo());

        return mimeMessage;
    }
}
