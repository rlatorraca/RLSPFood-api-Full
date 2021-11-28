package ca.com.rlsp.rlspfoodapi.api.v2.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CuisineInputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CuisineOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuisineInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;
    
    public Cuisine fromInputToController(CuisineInputDtoV2 cuisineInputDTO) {
        return modelMapper.map(cuisineInputDTO, Cuisine.class);
    }
    
    public void fromDTOtoCuisine(CuisineInputDtoV2 cuisineInputDTO, Cuisine cuisine) {
        modelMapper.map(cuisineInputDTO, cuisine);
    }   
} 