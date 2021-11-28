package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentTypeInputDto {

    public Long id;

    @NotBlank
    private String name;
}
