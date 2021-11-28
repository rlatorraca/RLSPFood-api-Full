package ca.com.rlsp.rlspfoodapi.core.security;

import org.springframework.context.annotation.Configuration;
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

    /**
     * Simetric Key
     * @return

    @Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec(
                "R0dr1g0L4t0rr4c4D3SP1R3SR0dr1g0L4t0rr4c4D3SP1R3SR0dr1g0L4t0rr4c4D3SP1R3S".getBytes(),
                "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }
    */


    //    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

    /**
     *  OLD ONE
     * Retira a tela de Login web a inicia a configuracao de autorizacao de acesso nas requisicoes
     * @param http
     * @throws Exception
     */
    /**
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/v1/cuisines/**").permitAll() // permite esse endpoint sem Autenticacao
                .anyRequest().authenticated() // autoriza qualquer autorizacao autenticada

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configura sessao sem ESTADO (sem cookies)
                .and()
                .csrf().disable(); // Cross-Site Request Forgery (CSRF) (no more cookies in the API)
    }
    */

    /**
     * Memory User Authentication
     */
    /** FEITO PELO "RLSPFood-api-Auth" (Authorization Server)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                    .password(passwordEncoder().encode("12345"))
                    .roles("ADMIN")
                .and()
                .withUser("rlsp")
                    .password(passwordEncoder().encode("1234"))
                    .roles("ADMIN");
    }

    /**
     * Password cryptography using BCrypt Encoder
     * @return
     */
    /**
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
      */

}
