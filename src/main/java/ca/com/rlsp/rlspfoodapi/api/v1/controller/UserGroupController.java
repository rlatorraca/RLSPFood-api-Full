package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.GroupModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.GroupOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.UserGroupControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import ca.com.rlsp.rlspfoodapi.domain.service.UserRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path="/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

    private UserRegistrationService userRegistrationService;
    private GroupModelAssembler groupModelAssembler;
    private BuildLinks buildLinks;
    private RlspFoodSecurity rlspFoodSecurity;

    public UserGroupController(UserRegistrationService userRegistrationService,
                               BuildLinks buildLinks,
                               GroupModelAssembler groupModelAssembler,
                               RlspFoodSecurity rlspFoodSecurity) {
        this.userRegistrationService = userRegistrationService;
        this.groupModelAssembler = groupModelAssembler;
        this.buildLinks = buildLinks;
        this.rlspFoodSecurity = rlspFoodSecurity;
    }

    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping
    //public List<GroupOutputDto> listAll(@PathVariable Long userId) {
    public CollectionModel<GroupOutputDto> listAll(@PathVariable Long userId) {
        User user = userRegistrationService.findOrFail(userId);
        CollectionModel<GroupOutputDto> groupOutputDtos = groupModelAssembler
                .toCollectionModel(user.getGroups())
                .removeLinks();
        if (rlspFoodSecurity.hasPermissionToEditUsersGroupsPermissions()) {

            groupOutputDtos.add(buildLinks.getLinkToGroupAttach(userId, "attach"));

            groupOutputDtos.getContent().forEach( groupOutputDto -> {
                groupOutputDto.add(buildLinks
                        .getLinkToUserGroupDetach(userId, groupOutputDto.getId(), "detach"));
            });
        }

        return groupOutputDtos;
        //return groupModelAssembler.fromControllerToOutputList(user.getGroups());
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detachGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.detachGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attachGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        userRegistrationService.attachGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }
}
