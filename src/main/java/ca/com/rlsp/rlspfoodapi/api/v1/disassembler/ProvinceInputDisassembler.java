package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProvinceInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.model.TaxProvince;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvinceInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Province fromInputToController(ProvinceInputDto provinceInputDTO) {
        return modelMapper.map(provinceInputDTO, Province.class);
    }
    
    public void fromDTOtoProvince(ProvinceInputDto provinceInputDTO, Province province) {
        province.setTax(new TaxProvince());

        modelMapper.map(provinceInputDTO, province);
    }   
} 