package ca.com.rlsp.rlspfoodapi.api.v2.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CityInputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;
    
    public City fromInputToController(CityInputDtoV2 cityInputDTO) {
        return modelMapper.map(cityInputDTO, City.class);
    }
    
    public void fromDTOtoCity(CityInputDtoV2 cityInputDTO, City city) {
        city.setProvince(new Province());
        modelMapper.map(cityInputDTO, city);
    }   
} 