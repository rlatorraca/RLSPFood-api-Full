package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputUpdateStatusDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Product fromInputToController(ProductInputDto productInputDto) {
        return modelMapper.map(productInputDto, Product.class);
    }
    
    public void fromDTOtoProductStatus(ProductInputUpdateStatusDto productInputDto, Product product) {
        modelMapper.map(productInputDto, product);
    }

    public void fromDTOtoProduct(ProductInputUpdateStatusDto productInputDto, Product product) {
        modelMapper.map(productInputDto, product);
    }
} 