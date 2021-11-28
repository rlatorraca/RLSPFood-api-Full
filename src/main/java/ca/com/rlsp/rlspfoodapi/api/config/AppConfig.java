package ca.com.rlsp.rlspfoodapi.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class AppConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistration() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8"); // use your preferred encoding
        filter.setForceEncoding(true); // force the encoding

        FilterRegistrationBean<CharacterEncodingFilter> registrationBean =
                new FilterRegistrationBean<>(filter); // register the filter
        registrationBean.addUrlPatterns("/*"); // set preferred url
        return registrationBean;
    }
}
