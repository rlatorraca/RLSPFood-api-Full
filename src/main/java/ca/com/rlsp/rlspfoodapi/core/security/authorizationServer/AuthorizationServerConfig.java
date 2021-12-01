package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    public static final String WRITE = "WRITE";
    public static final String PASSWORD = "password";
    public static final String READ = "READ";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String AUTHORIZATION_CODE = "authorization_code";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RlspFoodJwtKeyStoreProperties rlspFoodJwtKeyStoreProperties;

    @Autowired
    private DataSource dataSource;

    /**
     *  Usado para usar o Redis No-SQL para armazenar os tokens
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
     */

    //@formatter:off

    /**
     * Configura o Cliente para o Fluxo Password Credentials
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * Users in DB (sql)         *
         */
        clients.jdbc(dataSource);

        /**
         * Users In Memory Example

        clients.inMemory()
                // Clients => Password Credentials
                    .withClient("rlspfood-web") // Identificacao do Cliente (quem faz requisicao do Token  para o Authorization Server)
                    .secret(passwordEncoder.encode("123"))
                    .authorizedGrantTypes(PASSWORD, REFRESH_TOKEN) // Fluxo Password Credentials
                    .scopes(WRITE, READ)
                    .accessTokenValiditySeconds(60 * 60 * 6) // 60 sec * 60 min * 6h = 6hours (Access Token working time)
                    .refreshTokenValiditySeconds(60 * 60 * 24 * 2) // 60 sec * 60 min * 24h * 2d = 2 dias (Refresh Token working time)
                .and()
                    .withClient("rlspfood-mobile") // Identificacao do Cliente (quem faz requisicao do Token  para o Authorization Server)
                    .secret(passwordEncoder.encode("321"))
                    .authorizedGrantTypes(PASSWORD, REFRESH_TOKEN) // Fluxo Password Credentials
                    .scopes(WRITE, READ)

                // Client Crendentials
                .and()
                    .withClient("billing-token") // Identificacao do Cliente (quem faz requisicao do Token  para o Authorization Server)
                    .secret(passwordEncoder.encode("billing321"))
                    .authorizedGrantTypes(CLIENT_CREDENTIALS) // Fluxo Password Credentials
                    .scopes(WRITE, READ)

                // Clients => Authorization Code
                // Simple => http://auth.rlspfood.local:8082/oauth/authorize?response_type=code&client_id=food-analytics&state=R1SP&redirect_uri=http://www.foodanalytics.local:8084
                // PCKE Plain=> http://auth.rlspfood.local:8082/oauth/authorize?response_type=code&client_id=food-analytics&state=R1SP&redirect_uri=http://www.foodanalytics.local:8084&code_challenge=test123&code_challenge_method=plain
                // PCKE SHA256 => http://auth.rlspfood.local:8082/oauth/authorize?response_type=code&client_id=food-analytics&state=R1SP&redirect_uri=http://www.foodanalytics.local:8084&code_challenge=3ZNmLe2fEZNLrIBkxw-KE2AA7itu41zve1stpJ49ki0&code_challenge_method=s256
                // - code verifier(sha256 de 43 a 128 bits, sem parenteses) : R0dr1g0L4t0rr4c4D3S4nct1sP1r3s986532875421784512235689
                // - code challenge : base64url(sha 256 (code verifier)) : 3ZNmLe2fEZNLrIBkxw-KE2AA7itu41zve1stpJ49ki0
                // => Online PKCE Generator Tool : https://tonyxu-io.github.io/pkce-generator/
                .and()
                    .withClient("food-analytics") // Identificacao do Cliente (quem faz requisicao do Token  para o Authorization Server)
                    .secret(passwordEncoder.encode("analytics321"))
                    .authorizedGrantTypes(AUTHORIZATION_CODE) // Fluxo Password Credentials
                    .scopes(READ)
                    .redirectUris("http://client-app", "http://www.foodanalytics.local:8084")

                // Verify Token validate
                .and()
                    .withClient("check-token") // Identificacao do Cliente (quem faz requisicao do Token  para o Authorization Server)
                    .secret(passwordEncoder.encode("check321"))

                // Implicti Grant Type >> DON'T USE << - Just for testing
                // http://auth.rlspfood.local:8082/oauth/authorize?response_type=token&client_id=testImplictGrantType&state=R1SP&redirect_uri=http://test.implicit.grant.type
                .and()
                    .withClient("testImplictGrantType")
                .authorizedGrantTypes("implicit")
                .scopes(WRITE, READ)
                    .redirectUris("http://test.implicit.grant.type");
         */
    }
    //@formatter:on


    /**
     * Token Introspection (Check Token Validate)     *
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients() // aceita todas formas de autenticacao com ou sem client + secret
                .tokenKeyAccess("permitAll") // Para permitir peger PUBLIC KEY
                .checkTokenAccess("isAuthenticated()"); // para acessar o recurso de /check_token deve estar autenticado
        //security.checkTokenAccess("permitAll"); // Permite acesso sem autenticacao
    }

    /**
     *  Necessario APENAS para o Grant de Fluxo Password Credentials (precis do authentication manager)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception{
        // Usado para fazer a ADICAO de claims (novos atributos) no token
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(
                new JwtCustomClaimsTokenEnhancer(),
                jwtAccessTokenConverter()
        ));

        endpoint
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter())
                .approvalStore(getApprovalStore(endpoint.getTokenStore()))
                .tokenEnhancer(enhancerChain) // Usado para fazer a ADICAO de claims (novos atributos) no token
                // .tokenStore(getRedisTokenStore()) ==> Usado para usar o Redis No-SQL para armazenar os tokens
                .tokenGranter(tokenGranter(endpoint))
                .reuseRefreshTokens(false); // Fazer a renovacao do Refresh Token quando expirer (nao usar reutilizacao)

    }


    /**
     * Usado para gerar a CHAVE Publica JWT para checar se os tokens
     * @return
     */
    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) getKeyPair().getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("rlspfood-key-id");

        return new JWKSet(builder.build());
    }


    /**
     * Usado para usar o Redis No-SQL para armazenar os tokens
     * @return
    private RedisTokenStore getRedisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
     */

    /**
     * Implementa Chave SIMETRICA para o HMAC SHA-256
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
        /**
         * Simetric Key
         */
        // jwtAccessTokenConverter.setSigningKey("R0dr1g0L4t0rr4c4D3SP1R3SR0dr1g0L4t0rr4c4D3SP1R3SR0dr1g0L4t0rr4c4D3SP1R3S");



        jwtAccessTokenConverter.setKeyPair(getKeyPair());


        return jwtAccessTokenConverter;
    }

    private KeyPair getKeyPair() {
        /**
         * Assimetric key
         */
        var keyStorePass = rlspFoodJwtKeyStoreProperties.getPassword();
        var keyParAlias = rlspFoodJwtKeyStoreProperties.getKeyPairAlias();

        // Abre o arquivo para pegar as chaves
        var keyStoreKeyFactory = new KeyStoreKeyFactory(rlspFoodJwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());

        //Pega o par de chaves por meio do alias
        var keyPair = keyStoreKeyFactory.getKeyPair(keyParAlias);

        return keyPair;
    }

    /**
     * Tem a configuracao de todos os Tokens Granter (Authorization Code, Implicit, Cloud Credentials, etc) + PKCE     * @
     * @return
     */
    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

    /**
     * Permite a aprovação granular dos escopos (Write, read) , para a configuracao de Chave Assimétrica (Asymmetric Key)
     * @param tokenStore
     * @return
     */
    private ApprovalStore getApprovalStore(TokenStore tokenStore) {
        var approvalTokenStore = new TokenApprovalStore();
        approvalTokenStore.setTokenStore(tokenStore);
        return approvalTokenStore;
    }
}
