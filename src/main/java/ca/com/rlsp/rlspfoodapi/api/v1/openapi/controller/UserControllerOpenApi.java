package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PasswordInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.UserAndPasswordInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.UserInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.UserOutputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Users")
public interface UserControllerOpenApi {

    @ApiOperation("List al users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    CollectionModel<UserOutputDto> listaAll();


    @ApiOperation(value = "Get a user by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid user id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    UserOutputDto findById(@ApiParam(value = "clientId", example = "1", required = true)
                                          Long clientId);

    @ApiOperation(value = "Insert a user") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    UserOutputDto save( @ApiParam(name = "body", value = "A DTO for inputs a resource of user")
                                       UserAndPasswordInputDto clientAndPasswordInputDto);


    @ApiOperation(value = "Update data of a user by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    UserOutputDto update(@ApiParam(value = "clientId", example = "1", required = true)
                                        Long clientId,
                                @ApiParam(name = "body", value = "A DTO for inputs a resource of user")
                                        UserInputDto userInputDto);


    @ApiOperation("Update a user password")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Password changed successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    public void changePassword(@ApiParam(value = "userId", example = "1", required = true)
                                    Long userId,
                               @ApiParam(name = "body", value = "A DTO for inputs a resource of cuisine")
                                    PasswordInputDto passwordInputDto);
}
