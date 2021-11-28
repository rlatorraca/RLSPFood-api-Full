package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CityInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public City fromInputToController(CityInputDto cityInputDTO) {
        return modelMapper.map(cityInputDTO, City.class);
    }
    
    public void fromDTOtoCity(CityInputDto cityInputDTO, City city) {
        city.setProvince(new Province());
        modelMapper.map(cityInputDTO, city);
    }   
} 