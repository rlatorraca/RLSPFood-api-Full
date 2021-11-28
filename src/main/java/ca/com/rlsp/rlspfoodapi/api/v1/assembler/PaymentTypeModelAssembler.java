package ca.com.rlsp.rlspfoodapi.api.v1.assembler;

import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PaymentTypeOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentTypeModelAssembler extends RepresentationModelAssemblerSupport<PaymentType, PaymentTypeOutputDto> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BuildLinks buildlinks;
    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    public PaymentTypeModelAssembler() {
        super(PaymentType.class, PaymentTypeOutputDto.class);
    }


    /*
        Convert MODEL -> DTO para PUT
    */
    public PaymentTypeOutputDto fromControllerToOutput(PaymentType paymentType) {

        return modelMapper.map(paymentType, PaymentTypeOutputDto.class); // Faz o mapeamento substituindo o codigo acima
    }


    /*
        Convert MODEL -> DTO
    */
    public PaymentTypeOutputDto fromControllerToInput(PaymentType paymentType) {
        PaymentTypeOutputDto paymentTypeOutputDto = new PaymentTypeOutputDto();

        paymentTypeOutputDto.setId(paymentType.getId());
        paymentTypeOutputDto.setName(paymentType.getName());

        return paymentTypeOutputDto;
    }


    /*
        Convert MODEL -> DTO (list GET)
    */
    public List<PaymentTypeOutputDto> fromControllerToOutputList(Collection<PaymentType> paymentTypes){
        return paymentTypes.stream()
                .map(paymentType -> fromControllerToOutput(paymentType))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentTypeOutputDto toModel(PaymentType paymentType) {
        PaymentTypeOutputDto paymentTypeOutputDto =
                createModelWithId(paymentType.getId(), paymentType);

        modelMapper.map(paymentType, paymentTypeOutputDto);

        if(rlspFoodSecurity.hasPermissionToQueryPaymentTypes()){
            paymentTypeOutputDto.add(buildlinks.getLinkToPaymentType("payment Types"));
        }

        return paymentTypeOutputDto;
    }


    @Override
    public CollectionModel<PaymentTypeOutputDto> toCollectionModel(Iterable<? extends PaymentType> paymentTypes) {
        CollectionModel<PaymentTypeOutputDto> collectionModel = super.toCollectionModel(paymentTypes);

        if(rlspFoodSecurity.hasPermissionToQueryPaymentTypes()) {
            collectionModel.add(buildlinks.getLinkToPaymentType());
        }

        return collectionModel;
//        return super.toCollectionModel(paymentTypes)
//                .add(buildlinks.getLinkToPaymentType());
    }
    /*

    public Set<PaymentTypeOutputDto> fromControllerToOutputList(Set<PaymentType> paymentTypes){
        return paymentTypes.stream()
                .map(paymentType -> fromControllerToOutput(paymentType))
                .collect(Collectors.toSet());
    }
     */
}
