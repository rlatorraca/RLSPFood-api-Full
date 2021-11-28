package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.OrderInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Order fromInputToController(OrderInputDto orderInputDto) {
        return modelMapper.map(orderInputDto, Order.class);
    }
    
    public void fromDTOtoOrder(OrderInputDto orderInputDto, Order order) {
        modelMapper.map(orderInputDto, order);
    }   
} 