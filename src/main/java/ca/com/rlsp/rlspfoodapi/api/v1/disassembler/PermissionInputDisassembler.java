package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PermissionInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Permission fromInputToController(PermissionInputDto permissionInputDto) {
        return modelMapper.map(permissionInputDto, Permission.class);
    }
    
    public void fromDTOtoPermission(PermissionInputDto permissionInputDto, Permission permission) {

        modelMapper.map(permissionInputDto, permission);
    }   
} 