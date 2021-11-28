package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.UserController;
import ca.com.rlsp.rlspfoodapi.api.v1.controller.UserGroupController;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.UserOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserOutputDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public UserModelAssembler() {
        super(UserController.class, UserOutputDto.class);
    }

    public UserOutputDto fromControllerToOutput(User user) {
        UserOutputDto userOutputDto = createModelWithId(user.getId(), user);
        modelMapper.map(user, userOutputDto);
        return modelMapper.map(user, UserOutputDto.class);
    }

    public List<UserOutputDto> fromControllerToOutputList(Collection<User> users) {
        return users.stream()
                .map(client -> fromControllerToOutput(client))
                .collect(Collectors.toList());
    }

    @Override
    public UserOutputDto toModel(User user) {
        UserOutputDto userOutputDto = createModelWithId(user.getId(), user);
        modelMapper.map(user, userOutputDto);

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()){
            userOutputDto.add(
                    buildLinks.getLinkToUsers("users")
            );

//        userOutputDto.add(
//                linkTo(methodOn(UserController.class)
//                        .listaAll())
//                        .withRel("users")
//        );

            userOutputDto.add(
                    linkTo(methodOn(UserGroupController.class)
                            .listAll(user.getId()))
                            .withRel("user-groups")
            );

//        userOutputDto.add(
//                linkTo(methodOn(UserGroupController.class)
//                        .listAll(user.getId()))
//                        .withRel("user-groups")
//        );
        }


        return userOutputDto;
    }

    @Override
    public CollectionModel<UserOutputDto> toCollectionModel(Iterable<? extends User> users) {
        return super.toCollectionModel(users).add(buildLinks.getLinkToUsers());
        //return super.toCollectionModel(users).add(linkTo(CityController.class).withSelfRel());
    }
}
