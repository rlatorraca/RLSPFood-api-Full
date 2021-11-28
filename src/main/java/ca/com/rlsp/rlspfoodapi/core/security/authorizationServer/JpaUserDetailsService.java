package ca.com.rlsp.rlspfoodapi.core.security.authorizationServer;

import ca.com.rlsp.rlspfoodapi.domain.model.User;
import ca.com.rlsp.rlspfoodapi.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Usa a implementacao de >> UserDetailsService << para consultar os usuarios
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true) // Matem o Entity Manager aberto no "userRepository.findByEmail(username)"
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found on DB"));

        return new AuthenticationUser(user ,getAuthorities(user));
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        return user.getGroups().stream()
                .flatMap(group -> group.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getName().toUpperCase()))
                .collect(Collectors.toSet());

    }
}
