package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.OrderInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.filter.OrderFilterInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.OrderOutputDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Orders")
public interface OrderControllerOpenApi {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    value = "Properties names used to filter query, split by comma",
                    name = "fields ",
                    paramType = "query",
                    type = "string"
            )
    })
    @ApiOperation(value = "List all orders in JSON") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ordrs listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    PagedModel<OrderOutputDto> searchByFilterPageable(@ApiParam(name = "body",
            value = "A DTO for inputs a resource of orders")
                                                             OrderFilterInputDto orderFilter,
                                                             Pageable pageable);
    /*
    public Page<OrderOutputDto> searchByFilterPageable( @ApiParam(name = "body",
                                                                  value = "A DTO for inputs a resource of cuisine")
                                                                     OrderFilterInputDto orderFilter,
                                                       Pageable pageable);
    */


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    value = "Properties names used to filter query, split by comma",
                    name = "fields ",
                    paramType = "query",
                    type = "string"
            )
    })
    @ApiOperation(value = "List all orders in JSON") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    List<OrderOutputDto> searchByFilter(@ApiParam(name = "body",
                                                         value = "A DTO for inputs a resource of cuisine")
                                                            OrderFilterInputDto orderFilter);

    @ApiOperation(value = "List all orders in JSON") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    List<OrderOutputDto> listAll();


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    value = "Properties names used to filter query, split by comma",
                    name = "fields ",
                    paramType = "query",
                    type = "string"
            )
    })
    @ApiOperation(value = "Get a Orders by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid orders id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    OrderOutputDto find(@ApiParam(name = "orderCode", value = "Enter a valid order ID", example = "1", required = true)
                                           String orderCode);


    @ApiOperation(value = "Insert a order") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    OrderOutputDto add(@ApiParam(name = "body", value = "A DTO for inputs a resource of order")
                                          OrderInputDto orderInputDto);


}
