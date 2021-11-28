package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.GroupOutputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Api(tags = "Users")
public interface UserGroupControllerOpenApi {

    @ApiOperation(value = "Get all groups attached to a user") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    List<GroupOutputDto> listAll(@ApiParam(value = "userId", example = "1", required = true)
//                                                    Long userId);
    CollectionModel<GroupOutputDto> listAll(@ApiParam(value = "userId", example = "1", required = true)
                                                    Long userId);

    @ApiOperation("Detach a group of an user")
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
//    void detachGroup(@ApiParam(value = "userId", example = "1", required = true)
//                                        Long userId,
//                            @ApiParam(value = "groupId", example = "1", required = true)
//                                    Long groupId);
    ResponseEntity<Void> detachGroup(@ApiParam(value = "userId", example = "1", required = true)
                                        Long userId,
                     @ApiParam(value = "groupId", example = "1", required = true)
                                    Long groupId);

    @ApiOperation("Attach a group of an user")
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
//    void attachGroup(@ApiParam(value = "userId", example = "1", required = true)
//                                        Long userId,
//                            @ApiParam(value = "groupId", example = "1", required = true)
//                                        Long groupId);
    ResponseEntity<Void> attachGroup(@ApiParam(value = "userId", example = "1", required = true)
                                        Long userId,
                            @ApiParam(value = "groupId", example = "1", required = true)
                                        Long groupId);
}
