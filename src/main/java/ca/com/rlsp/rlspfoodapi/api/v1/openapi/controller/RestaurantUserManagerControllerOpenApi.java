package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
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
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurants")
public interface RestaurantUserManagerControllerOpenApi {

    @ApiOperation(value = "User list that manager restaurant") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    CollectionModel<UserOutputDto> listOne(Long restaurantId);

    @ApiOperation("Detach a user as a restaurant manager")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Detach successfully done",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                          //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant and/or Payment Type not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    void detachManager(@ApiParam(value = "restaurantId", example = "1", required = true)
//                                          Long restaurantId,
//                              @ApiParam(value = "userId", example = "1", required = true)
//                                          Long userId);

    ResponseEntity<Void> detachManager(@ApiParam(value = "restaurantId", example = "1", required = true)
                                          Long restaurantId,
                              @ApiParam(value = "userId", example = "1", required = true)
                                          Long userId);


    @ApiOperation("Attach a user as a restaurant manager")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Attach successfully done",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant and/or User not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
//    void attachManager(@ApiParam(value = "restaurantId", example = "1", required = true)
//                                          Long restaurantId,
//                             @ApiParam(value = "userId", example = "1", required = true)
//                                          Long userId);
    ResponseEntity<Void> attachManager(@ApiParam(value = "restaurantId", example = "1", required = true)
                                          Long restaurantId,
                                       @ApiParam(value = "userId", example = "1", required = true)
                                          Long userId);
}

