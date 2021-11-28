package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.GroupModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.GroupControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.GroupInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.GroupInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.GroupOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Group;
import ca.com.rlsp.rlspfoodapi.domain.repository.GroupRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.GroupRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping("/groups")
@RequestMapping(path = "/v1/groups")
public class GroupController implements GroupControllerOpenApi {

    private GroupRepository groupRepository;
    private GroupModelAssembler groupModelAssembler;
    private GroupRegistrationService groupRegistrationService;
    private GroupInputDisassembler groupInputDisassembler;


    public GroupController(GroupRepository groupRepository,
                           GroupModelAssembler groupModelAssembler,
                           GroupRegistrationService groupRegistrationService,
                           GroupInputDisassembler groupInputDisassembler) {
        this.groupRepository = groupRepository;
        this.groupModelAssembler = groupModelAssembler;
        this.groupRegistrationService = groupRegistrationService;
        this.groupInputDisassembler = groupInputDisassembler;
    }

    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping
    //public List<GroupOutputDto> listAll() {
    public CollectionModel<GroupOutputDto> listAll() {
        List<Group> allGroups = groupRepository.findAll();

        return groupModelAssembler.toCollectionModel(allGroups);
        //return groupModelAssembler.fromControllerToOutputList(todosGrupos);
    }

    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping("/{groupId}")
    //public City findById(@PathVariable Long cityId) {
    public GroupOutputDto findById(@PathVariable("groupId") Long id) {
        Group group = groupRegistrationService.findOrFail(id);


        //  return cityRegistrationService.findOrFail(cityId);

        return groupModelAssembler.fromControllerToOutput(group);
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupOutputDto save(@RequestBody @Valid GroupInputDto groupInput){
        Group group = groupInputDisassembler.fromInputToController(groupInput);
        group =  groupRegistrationService.save(group);
        return groupModelAssembler.fromControllerToOutput(group);
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @PutMapping("{groupId}")
    public GroupOutputDto update(@PathVariable("groupId") Long id,
                                 @RequestBody @Valid GroupInputDto groupInputDto){
        Group currentGroup = groupRegistrationService.findOrFail(id);
        groupInputDisassembler.fromDTOtoGroup(groupInputDto, currentGroup);
        currentGroup = groupRegistrationService.save(currentGroup);
        return groupModelAssembler.fromControllerToOutput(currentGroup);
    }

    @CheckSecurity.UserGroup.hasPermissionToEdit
    @Override
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("grupoId") Long id) {
        groupRegistrationService.delete(id);
    }
}
