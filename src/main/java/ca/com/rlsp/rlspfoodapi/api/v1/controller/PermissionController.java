package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.PermissionModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PermissionOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.PermissionControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Permission;
import ca.com.rlsp.rlspfoodapi.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionModelAssembler permissionModelAssembler;


    @CheckSecurity.UserGroup.hasPermissionToQuery
    @Override
    @GetMapping
    public CollectionModel<PermissionOutputDto> listAll() {
        List<Permission> allPermissions = permissionRepository.findAll();

        return permissionModelAssembler.toCollectionModel(allPermissions);

    }
}
