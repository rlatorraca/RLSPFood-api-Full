package ca.com.rlsp.rlspfoodapi.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Injeta o interceptador de metodos para dizer que o metodo esta depracados
    @Autowired
    private ApiDeprecationHandler apiDeprecationHandler;

    // Injeta o interceptador de metodos para dizer que o metodo esta RETIRED
    @Autowired
    private ApiRetirementHandler apiRetirementHandler;

    // Habilita o CORS globalmente na aplicacao
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*") // metodos permitidos (* = todos)
                .allowedOrigins("*") // Origins permitidos (* = todos)
                .maxAge(600); // cache do tempo do preflight do CORS (10 minutos no caso)
    }


    /*
        - Introduzido Automaticamente onde se "cacheControle" habilitado
        - Gera o HASH do Body da resposta para o CACHE ETag
        - Verifica se o HASH criado pelo sistema concide com a eTag que esta no atributo do cabecalho "if-None-Match"
        - Se igual retorna 304 (not modified)
*/
    @Bean
    public Filter shallowETagHeaderFilter(){
       return  new ShallowEtagHeaderFilter(); // Gera o HASH do Body da resposta para o CACHE ETag
    }

    /*
        Define qual a versao da Aplicacao => V2 no caso
     */

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        WebMvcConfigurer
                .super
                    .configureContentNegotiation(
                            configurer.defaultContentType(RlspFoodVersionMediaType.V2_APPLICATION_JSON
                            ));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //registry.addInterceptor(apiDeprecationHandler);
        registry.addInterceptor(apiRetirementHandler);
    }
}
