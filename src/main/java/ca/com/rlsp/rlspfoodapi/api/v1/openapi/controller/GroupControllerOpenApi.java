package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.GroupInputDto;
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

@Api(tags = "Groups")
public interface GroupControllerOpenApi {

    @ApiOperation(value = "List all groups in JSON")
    //public List<GroupOutputDto> listAll() ;
    public CollectionModel<GroupOutputDto> listAll() ;

    @ApiOperation(value = "Get a Group by ID") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid group id",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Group not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    GroupOutputDto findById(@ApiParam(value = "Group id", example = "1", required = true)
                                               Long id);

    @ApiOperation(value = "Insert a group") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Group created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                            )
            )
    })
    GroupOutputDto save( @ApiParam(name = "body", value = "A DTO for inputs a resource of group" , required =true)
                                            GroupInputDto groupInput);

    @ApiOperation(value = "Update data of a group by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Group updated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Group not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    GroupOutputDto update(@ApiParam(value = "Group Id", example = "1", required = true)
            Long id,
            @ApiParam(name = "body", value = "A DTO for inputs a resource of group" , required =true)
            GroupInputDto groupInputDto);

    @ApiOperation("Remove a group")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Group removed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Group not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    void remove(@ApiParam(name = "body", value = "A DTO for inputs a resource of group" , required =true)
                                   Long id);
}
