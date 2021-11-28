package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PaymentTypeOutputDto;
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

@Api(tags = "Restaurant Products")
public interface RestaurantPaymentTypeControllerOpenApi {

    @ApiOperation(value = "Get all payment types by restaurant Id") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    //List<PaymentTypeOutputDto> listAllByRestaurantId(@ApiParam(value = "restaurantId", example = "1", required = true)
    //                                                                      Long restaurantId);
    CollectionModel<PaymentTypeOutputDto> listAllByRestaurantId(@ApiParam(value = "restaurantId", example = "1", required = true)
                                                                          Long restaurantId);

    @ApiOperation("Detach a payment type of a restaurant")  // Customize method description on SwaggerUI
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
//    void detachPaymentType(@ApiParam(value = "PaymentTypeId", example = "1", required = true)
//                                          Long paymentTypeId,
//                                  @ApiParam(value = "restaurantId", example = "1", required = true)
//                                          Long restaurantId);

    ResponseEntity<Void> detachPaymentType(@ApiParam(value = "PaymentTypeId", example = "1", required = true)
                                          Long paymentTypeId,
                                  @ApiParam(value = "restaurantId", example = "1", required = true)
                                          Long restaurantId);


    @ApiOperation("Attach a payment type of a restaurant")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Attach successfully done",
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
//    void attachPaymentType(@ApiParam(value = "PaymentTypeId", example = "1", required = true)
//                                          Long paymentTypeId,
//                                  @ApiParam(value = "restaurantId", example = "1", required = true)
//                                          Long restaurantId);
    ResponseEntity<Void> attachPaymentType(@ApiParam(value = "PaymentTypeId", example = "1", required = true)
                                          Long paymentTypeId,
                           @ApiParam(value = "restaurantId", example = "1", required = true)
                                          Long restaurantId);
}
