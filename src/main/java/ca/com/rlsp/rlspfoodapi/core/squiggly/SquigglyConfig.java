package ca.com.rlsp.rlspfoodapi.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {

        // inicializa o Squiggly com os campos para serem filtrados
        //Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null)); // Mudando o nome de FIELD (padrao) para outro nome
        Squiggly.init(objectMapper, new RequestSquigglyContextProvider());

        // para os endypoints ORDERS e RESTAURANTS
        var urlAuthorized = Arrays.asList("/orders/*", "/restaurants/*");
        var filterRegistration = new FilterRegistrationBean<SquigglyRequestFilter>();
        filterRegistration.setFilter(new SquigglyRequestFilter());
        filterRegistration.setOrder(1);

        return filterRegistration;
    }
}
