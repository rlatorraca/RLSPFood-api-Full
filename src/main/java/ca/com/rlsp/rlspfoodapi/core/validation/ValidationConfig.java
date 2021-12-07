package ca.com.rlsp.rlspfoodapi.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

    /*
        Faz a integracao do Spring com o Hibernate Bean Validator
     */
    @Bean
    @Primary
    public LocalValidatorFactoryBean validator(MessageSource messageSource){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource); // Faz o set para usar nessage.properties (SPRING) em vez do ValidationMessages.properties
        return bean;
    }
}
