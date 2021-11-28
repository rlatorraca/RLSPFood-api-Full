package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuisineInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Cuisine fromInputToController(CuisineInputDto cuisineInputDTO) {
        return modelMapper.map(cuisineInputDTO, Cuisine.class);
    }
    
    public void fromDTOtoCuisine(CuisineInputDto cuisineInputDTO, Cuisine cuisine) {
        modelMapper.map(cuisineInputDTO, cuisine);
    }   
} 