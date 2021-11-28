package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PermissionInputDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
