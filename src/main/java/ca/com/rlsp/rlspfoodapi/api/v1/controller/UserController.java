package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.UserModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.UserInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.UserAndPasswordInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.UserInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PasswordInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.UserOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.UserControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.exception.ProvinceNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import ca.com.rlsp.rlspfoodapi.domain.repository.UserRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.UserRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(path = "/users")
@RequestMapping(path = "/v1/users")
public class UserController implements UserControllerOpenApi {

    private UserRepository userRepository;
    private UserRegistrationService userRegistrationService;
    private UserModelAssembler userModelAssembler;
    private UserInputDisassembler userInputDisassembler;
    private RlspFoodSecurity rlspFoodSecurity;

    public UserController(UserRepository userRepository,
                          UserRegistrationService userRegistrationService,
                          UserModelAssembler userModelAssembler,
                          UserInputDisassembler userInputDisassembler,
                          RlspFoodSecurity rlspFoodSecurity ) {

        this.userRepository = userRepository;
        this.userRegistrationService = userRegistrationService;
        this.userModelAssembler = userModelAssembler;
        this.userInputDisassembler = userInputDisassembler;
        this.rlspFoodSecurity = rlspFoodSecurity;
    }

    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping
    public CollectionModel<UserOutputDto> listaAll() {
        List<User> allUsers = userRepository.findAll();

        return userModelAssembler.toCollectionModel(allUsers);
    }

    /*
    @GetMapping
    public List<UserOutputDto> listaAll() {
        List<User> allUsers = userRepository.findAll();

        return userModelAssembler.fromControllerToOutputList(allUsers);
    }
     */

    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping("/{userId}")
    public UserOutputDto findById(@PathVariable("userId") Long id) {
        User user = userRegistrationService.findOrFail(id);

        return userModelAssembler.toModel(user);
        //return userModelAssembler.fromControllerToOutput(user);
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDto save(@RequestBody @Valid UserAndPasswordInputDto clientAndPasswordInputDto) {
        try{
            User user = userInputDisassembler.fromInputToController(clientAndPasswordInputDto);
            user = userRegistrationService.save(user);

            return userModelAssembler.toModel(user);
            //return userModelAssembler.fromControllerToOutput(user);
        } catch (EntityNotFoundException e){
            throw new GenericBusinessException(e.getReason(), e);
        }

    }

    @CheckSecurity.UserGroup.hasPermissionToChangeUserData
    @Override
    @PutMapping("/{userId}")
    public UserOutputDto update(@PathVariable("userId") Long id,
                                @RequestBody @Valid UserInputDto userInputDto) {

        try{
            User currentUser = userRegistrationService.findOrFail(id);
            userInputDisassembler.fromDTOtoClient(userInputDto, currentUser);
            currentUser = userRegistrationService.save(currentUser);

            return userModelAssembler.toModel(currentUser);
            //return userModelAssembler.fromControllerToOutput(currentUser);
        } catch (ProvinceNotFoundException e ) {
            throw new GenericBusinessException(e.getReason(), e);
        }


    }
    @CheckSecurity.UserGroup.hasPermissionToChangeOwnPassword
    @Override
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable(value = "userId") Long userId, @RequestBody @Valid PasswordInputDto passwordInputDto) {
        userRegistrationService.changePassword(userId, passwordInputDto.getCurrantPassword(), passwordInputDto.getNewPassword());
    }
}
