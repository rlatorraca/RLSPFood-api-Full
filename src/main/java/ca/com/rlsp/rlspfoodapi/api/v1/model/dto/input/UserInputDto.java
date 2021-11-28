package ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInputDto {

    @ApiModelProperty(example = "Ian Strovski", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "ianstrovski@gmail.com", required = true)
    @NotBlank
    @Email
    private String email;



}
