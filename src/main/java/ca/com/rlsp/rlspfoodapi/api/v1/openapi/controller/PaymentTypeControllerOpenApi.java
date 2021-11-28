package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PaymentTypeInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PaymentTypeOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.model.PaymentsTypesModelOpenApi;
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
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Payment Types")
public interface PaymentTypeControllerOpenApi {

// => Problema com response = PaymentsTypesModelOpenApi.class (swagger V3)
//    @ApiOperation(value = "List all payment types in JSON", response = PaymentsTypesModelOpenApi.class) // Customize method description on SwaggerUI
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Cities listed in JSON",
//                    content = @Content(
//                            mediaType = MediaType.APPLICATION_JSON_VALUE
//
//                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
//                    )
//            )
//    })

    // Swagger V2
    @io.swagger.annotations.ApiOperation(value = "List all payment types in JSON") // Customize method description on SwaggerUI
    @io.swagger.annotations.ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Cities listed in JSON",
                    response = PaymentsTypesModelOpenApi.class)})
    //public ResponseEntity<List<PaymentTypeOutputDto>> listAll(ServletWebRequest request);
    ResponseEntity<CollectionModel<PaymentTypeOutputDto>> listAll(ServletWebRequest request);



    @ApiOperation(value = "Get a Payment Type by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid payment type id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "PAyment type not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    ResponseEntity<PaymentTypeOutputDto> findById(@ApiParam(name = "paymentTypeId", value = "Enter a valid payment type ID", example = "1", required = true)
                                                                     Long paymentTypeId, ServletWebRequest request);

    @ApiOperation(value = "Insert a payment type") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment type created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                            )
            )
    })
    PaymentTypeOutputDto save(@ApiParam(name = "body", value = "A DTO for inputs a resource of payment type", required =true) PaymentTypeInputDto paymentTypeInputDto);

    @ApiOperation(value = "Update data of a city by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    PaymentTypeOutputDto update(@ApiParam(name="paymentTypeId" , value= "Enter a valid payment type ID", example = "1", required =true) Long paymentTypeId,
                                       @ApiParam(name = "body", value = "A DTO for inputs a resource of payment type" , required =true)
                                               PaymentTypeInputDto paymentTypeInputDto);


    @ApiOperation("Remove a payment type")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment type removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Payment type not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    void remove(@ApiParam(name="paymentTypeId" , value= "Enter a valid payment type ID", example = "1", required =true)
                                   Long formaPagamentoId);
}
