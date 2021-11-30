package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Para devolver um paginna HTNK
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/oauth/confirm_access")
    public String approval() {
        return "pages/approval";
    }
}
