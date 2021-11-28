package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProvinceIdInputDto {

    @NotNull
    private Long id;

}
