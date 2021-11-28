package ca.com.rlsp.rlspfoodapi.api.v1.disassembler;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PaymentTypeInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentTypeInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public PaymentType fromInputToController(PaymentTypeInputDto paymentTypeInputDto) {
        return modelMapper.map(paymentTypeInputDto, PaymentType.class);
    }

    public void fromDTOtoPaymentType(PaymentTypeInputDto paymentTypeInputDto, PaymentType paymentType) {
        modelMapper.map(paymentTypeInputDto, paymentType);
    }
}
