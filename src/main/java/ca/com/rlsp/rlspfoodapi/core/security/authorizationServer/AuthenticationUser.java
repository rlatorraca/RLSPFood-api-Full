package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthenticationUser extends User {

    private String fullName;
    private Long id;

    public AuthenticationUser(ca.com.rlsp.rlspfoodapi.domain.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);

        this.fullName = user.getName();
        this.id = user.getId();
    }
}
