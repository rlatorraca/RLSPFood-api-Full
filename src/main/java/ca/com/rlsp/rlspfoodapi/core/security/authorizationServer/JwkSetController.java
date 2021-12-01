package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import com.nimbusds.jose.jwk.JWKSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwkSetController {

    @Autowired
    private JWKSet jwkSet;


    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        Logger logger = LoggerFactory.getLogger("RLSPFood Looger");
        logger.info("Hi .... Jwt Checked");

        return this.jwkSet.toJSONObject();
    }
}
