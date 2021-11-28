package ca.com.rlsp.rlspfoodapi.infra.service.email;

import ca.com.rlsp.rlspfoodapi.core.email.EmailProperties;
import ca.com.rlsp.rlspfoodapi.domain.service.SendEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

//@Service
public class SmtpSendEmailService implements SendEmailService {

    public static final String MSG_EMAIL_NOT_SEND = "System cannot send the email.";
    private static final String MSG_ERROR_PROCESSING_EMAIL = "Error processing email template.";
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfiguration; // Injeta o Freemarker processador de Template de Emails

    @Override
    public void send(Message message) {

        try {

            MimeMessage mimeMessage = buildMimeMessage(message);

            javaMailSender.send(mimeMessage);
        } catch (Exception e){
            throw new EmailException(MSG_EMAIL_NOT_SEND, e);
        }

    }

    protected MimeMessage buildMimeMessage(Message message) throws MessagingException {
        String emailBodyProcessed = processTemplateFreeMarker(message);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        mimeMessageHelper.setFrom(emailProperties.getSender());
        mimeMessageHelper.setTo(message.getDestinations().toArray(new String[0]));
        mimeMessageHelper.setSubject(message.getSubject());
        mimeMessageHelper.setText(emailBodyProcessed, true); // true, pois sera em HTML
        return mimeMessage;
    }

    protected String processTemplateFreeMarker(Message message){
        try {
            Template template = freemarkerConfiguration.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getTemplateAttributes());
        } catch (Exception e) {
            throw new EmailException(MSG_ERROR_PROCESSING_EMAIL, e);
        }

    }
}
