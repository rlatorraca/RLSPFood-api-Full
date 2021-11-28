package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.UserInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public User fromInputToController(UserInputDto userInputDto) {
        return modelMapper.map(userInputDto, User.class);
    }
    
    public void fromDTOtoClient(UserInputDto userInputDto, User user) {

        modelMapper.map(userInputDto, user);
    }   
} 