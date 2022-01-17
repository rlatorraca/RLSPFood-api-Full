package ca.com.rlsp.rlspfoodapi.infra.service.email;

import ca.com.rlsp.rlspfoodapi.domain.service.SendEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Component
public class ProcessorTemplateEmail {

    private static final String MSG_ERROR_PROCESSING_EMAIL = "Error processing email template.";

    @Autowired
    private Configuration freemarkerConfiguration; // Injeta o Freemarker processador de Template de Emails

    protected String processTemplateFreeMarker(SendEmailService.Message message){
        try {
            Template template = freemarkerConfiguration.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getTemplateAttributes());
        } catch (Exception e) {
            throw new EmailException(MSG_ERROR_PROCESSING_EMAIL, e);
        }

    }
}
