package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.CityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.model.Group;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import ca.com.rlsp.rlspfoodapi.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegistrationService {

    public static final String MSG_CLIENT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE = "Client of code %d  %d not found into the Database";
    public static final String MSG_CLIENT_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY = "Client of code %d cannot be removed, because that is being used as  secondary key";
    public static final String MSG_PASSWORDS_NOT_MATCHES = "Passwords not matches.";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private GroupRegistrationService groupRegistrationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user){
        userRepository.detach(user);
        Optional<User> clientFound = userRepository.findByEmail(user.getEmail());

        if( clientFound.isPresent() && !clientFound.get().equals(user)) {
            throw new GenericBusinessException(String.format("Email %s already used by another client", user.getEmail()));
        }

        if (user.isNewUser()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public void remove(Long id){
        try{
            userRepository.deleteById(id);
            userRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new CityNotFoundException(
                    String.format(MSG_CLIENT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityIsForeignKeyException(
                    String.format(MSG_CLIENT_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY, id)
            );
        }
    }

    @Transactional
    public void changePassword(Long clientId, String currentPassword, String newPassword) {
        User user = findOrFail(clientId);

        //if (usuario.passwordNotMatches(currentPassword)) {
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new GenericBusinessException(MSG_PASSWORDS_NOT_MATCHES);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void detachGroup(Long userId, Long groupId) {
        User user = findOrFail(userId);
        Group group = groupRegistrationService.findOrFail(groupId);

        user.detachGroup(group);
    }

    @Transactional
    public void attachGroup(Long userId, Long groupId) {
        User user = findOrFail(userId);
        Group group = groupRegistrationService.findOrFail(groupId);

        user.attachGroup(group);
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id){
        try{
            return  userRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(
                    String.format(MSG_CLIENT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        }
    }

    public User findOrFail(Long clientId){
        return userRepository.findById(clientId).orElseThrow(()-> new CityNotFoundException(String.format(MSG_CLIENT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, clientId)));
    }
}

