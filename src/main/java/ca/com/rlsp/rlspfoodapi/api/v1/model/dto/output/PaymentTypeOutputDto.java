package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "paymentType")
@Getter
@Setter
public class PaymentTypeOutputDto extends RepresentationModel<PaymentTypeOutputDto> {

    private Long id;
    private String name;
}
