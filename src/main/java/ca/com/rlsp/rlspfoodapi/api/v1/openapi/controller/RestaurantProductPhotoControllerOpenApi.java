package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductPhotoInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProductPhotoOutputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(tags = "Restaurant Products Photos")
public interface RestaurantProductPhotoControllerOpenApi {

    @ApiOperation(value = "Get a product photo of a restaurant",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id and/or product id",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product photo not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ProductPhotoOutputDto findProductPhoto(@ApiParam(value = "restaurantId", example = "1", required = true)
                                                          Long restaurantId,
                                                  @ApiParam(value = "productId", example = "1", required = true)
                                                          Long productId) ;

    @ApiOperation(value = "Get a product photo of a restaurant" , hidden = true)
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id and/or product id",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product photo not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ResponseEntity<?> getProductPhoto(@ApiParam(value = "restaurantId", example = "1", required = true)
                                                     Long restaurantId,
                                             @ApiParam(value = "productId", example = "1", required = true)
                                                     Long productId,
                                             @RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException;

    @ApiOperation(value = "Update a product photo of a restaurant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product photo updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant and/or product  not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ProductPhotoOutputDto updatePhoto(@ApiParam(value = "restaurantId", example = "1", required = true)
                                             Long restaurantId,
                                      @ApiParam(value = "productId", example = "1", required = true)
                                             Long productId,
                                      @ApiParam(value = "Photo file (max 500KB, just JPG and PNG files)", required = true)
                                             MultipartFile fileSent ,
                                      ProductPhotoInputDto photoProductInput) throws IOException;
    ;

    @ApiOperation(value = "Delete product photo of a restaurant") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id and/or product id",
                    content = @Content(
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Product photo not found",
                    content = @Content(
                            schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    void deletePhoto(@ApiParam(value = "restaurantId", example = "1", required = true)
                                    Long restaurantId,
                            @ApiParam(value = "productId", example = "1", required = true)
                                    Long productId) ;

}
