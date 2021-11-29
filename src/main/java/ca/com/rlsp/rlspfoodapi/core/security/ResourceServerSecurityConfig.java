package ca.com.rlsp.rlspfoodapi.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Setup the Spring Security on the API
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // permissoes nos metodos individualmente
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Retira a tela de Login web a inicia a configuracao de autorizacao de acesso nas requisicoes
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .formLogin()
                .and()
                .authorizeRequests().antMatchers("/oath/**").authenticated()
                .and()
            //.authorizeRequests()
              //.antMatchers(HttpMethod.POST, "/v2/cuisines/**").hasAuthority("EDIT_CUISINE")
              //.antMatchers(HttpMethod.PUT, "/v2/cuisines/**").hasAuthority("EDIT_CUISINE")
              //.antMatchers(HttpMethod.GET, "/v2/cuisines/**").authenticated()
                //.anyRequest().authenticated()
                //.anyRequest().denyAll()
            //.and()
                //.oauth2ResourceServer() // Habilita um "Resource Server" na API
                //.opaqueToken();  // opaqueToken (sem possibilidade de leitura <> do JWT (possivel leitura))
                .oauth2ResourceServer()
                    .jwt()//Usando./ jwt em vez "opaqueToken"
                    .jwtAuthenticationConverter(getJwtAuthenticationConverter());
    }

    /**
     * Faz a conversao/carregamento de JWT para uma lista de Authorities permitidas
     */
    private JwtAuthenticationConverter getJwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter( jwt -> {
            var authorities = jwt.getClaimAsStringList("authorities"); // retorna um Lista de String de Auhtorithies
            if(authorities == null ){
                authorities = Collections.emptyList();
            }

            // Add SCOPES (READ, WRITE)
            var scopes = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> grantedAuthorities = scopes.convert(jwt);


            // Add PERMISSIONS/AUTHORITIES
            var permissions = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            grantedAuthorities.addAll(permissions);

            // Converte para uma List SinpleGrantedAuthorithy em vez de uma List<tring>
            return grantedAuthorities;
        });

        return jwtAuthenticationConverter;
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
