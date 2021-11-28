package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputUpdateStatusDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProductOutputDto;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

@Api(tags = "Products")
public interface RestaurantProductControllerOpenApi  {


    @ApiOperation(value = "List all products") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid group id",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Group not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
//    List<ProductOutputDto> listAllActives(@ApiParam(value = "restaurantId", example = "1", required = true)
//                                                              Long restaurantId,
//                                                 @ApiParam(value = "Active or Inactive products on list",
//                                                          example = "false", defaultValue = "false")
//                                                         Boolean justActiveProducts);

    CollectionModel<ProductOutputDto> listAllActives(@ApiParam(value = "restaurantId", example = "1", required = true)
                                                              Long restaurantId,
                                                     @ApiParam(value = "Active or Inactive products on list",
                                                          example = "false", defaultValue = "false")
                                                         Boolean justActiveProducts);



    @ApiOperation(value = "Get a restaurant product") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant and/or product not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ProductOutputDto findByRestaurantIdAndByProductId(@ApiParam(value = "restaurantId", example = "1", required = true)
                                               Long restaurantId,
                                                      @ApiParam(value = "productId", example = "1", required = true)
                                           Long productId) ;

    @ApiOperation(value = "Update in a restaurant a data of a product by ID") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant and/or product  not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ProductOutputDto update(@ApiParam(value = "restaurantId", example = "1", required = true)
                                               Long restaurantId,
                                   @ApiParam(value = "productId", example = "1", required = true)
                                           Long productId,
                                   @ApiParam(name = "body", value = "A DTO for inputs a resource of product")
                                               ProductInputDto productInputDto);;

    ProductOutputDto updateJustStatus(@ApiParam(value = "restaurantId", example = "1", required = true)
                                                     Long restaurantId,
                                             @ApiParam(value = "productId", example = "1", required = true)
                                                     Long productId,
                                             @ApiParam(name = "body", value = "A DTO for inputs a resource of product")
                                                     ProductInputUpdateStatusDto productInputDto);


    @ApiOperation(value = "Insert a product to restaurant")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),

            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    ProductOutputDto save(@ApiParam(value = "restaurantId", example = "1", required = true)
                                              Long restaurantId,
                          @ApiParam(name = "body", value = "A DTO for inputs a resource of product")
                                              ProductInputDto productInputDto);

    @ApiOperation("Remove a product")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    void delete(@ApiParam(value = "productId", example = "1", required = true)
                                Long id);
}
