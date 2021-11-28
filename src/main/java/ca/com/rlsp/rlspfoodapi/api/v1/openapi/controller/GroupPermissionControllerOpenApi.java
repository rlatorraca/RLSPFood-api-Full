package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PermissionOutputDto;
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

@Api(tags = "Groups")
public interface GroupPermissionControllerOpenApi {

    @ApiOperation("List permissions associated a one group")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid permission id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    List<PermissionOutputDto> listAll(@ApiParam(value = "groupId", example = "1", required = true)
//                                                        Long groupId) ;
    CollectionModel<PermissionOutputDto> listAll(@ApiParam(value = "groupId", example = "1", required = true)
                                                        Long groupId) ;


    @ApiOperation("Detach a group permission")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission Group detached",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                            )
            ),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    void detach(@ApiParam(value = "groupId", example = "1", required = true)
//                                   Long groupId,
//                       @ApiParam(value = "permissionId", example = "1", required = true)
//                               Long permissionId) ;
    ResponseEntity<Void> detach(@ApiParam(value = "groupId", example = "1", required = true)
                                   Long groupId,
                                @ApiParam(value = "permissionId", example = "1", required = true)
                               Long permissionId) ;


    @ApiOperation("Attach a group permission")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Permission Group Attached",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Permission not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    void attach(@ApiParam(value = "groupId", example = "1", required = true)
//                                   Long groupId,
//                       @ApiParam(value = "permissionId", example = "1", required = true)
//                                   Long permissionId) ;

    ResponseEntity<Void> attach(@ApiParam(value = "groupId", example = "1", required = true)
                                   Long groupId,
                       @ApiParam(value = "permissionId", example = "1", required = true)
                                   Long permissionId) ;
}
