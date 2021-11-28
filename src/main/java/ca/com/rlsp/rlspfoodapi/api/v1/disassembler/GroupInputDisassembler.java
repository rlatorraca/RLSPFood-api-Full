package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.GroupInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Group fromInputToController(GroupInputDto groupInputDto) {
        return modelMapper.map(groupInputDto, Group.class);
    }
    
    public void fromDTOtoGroup(GroupInputDto groupInputDto, Group group) {

        modelMapper.map(groupInputDto, group);
    }   
} 