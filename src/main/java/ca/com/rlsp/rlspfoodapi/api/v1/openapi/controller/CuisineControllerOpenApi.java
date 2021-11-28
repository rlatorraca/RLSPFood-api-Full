package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Cuisines")
public interface CuisineControllerOpenApi {

    @ApiOperation(value = "List all cuisines in JSON") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    //List<CuisineOutputDto> listAll();
    CollectionModel<CuisineOutputDto> listAll();



    @ApiOperation(value = "List all cities in JSON") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    List<CuisineOutputDto> listAllPageableList(Pageable pageable);


    @ApiOperation(value = "Get a Cuisine by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid city id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    CuisineOutputDto findBy1Id(@ApiParam(name = "cuisineId", value = "Enter a valid cuisine ID", example = "1", required = true) Long id);



    @ApiOperation(value = "Insert a cuisine") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cuisine created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    CuisineOutputDto save(@ApiParam(name = "body", value = "A DTO for inputs a resource of cuisine")
                                             CuisineInputDto cuisineInputDTO);

    @ApiOperation(value = "Update data of a cuisine by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cuisine updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    CuisineOutputDto updateById(@ApiParam(name="cuisineId" , value= "Enter a valid cuisine ID", example = "1", required =true)
                                                   Long id,
                                       @ApiParam(name = "body", value = "A DTO for inputs a resource of cuisine")
                                       CuisineInputDto cuisineInputDTO);


    @ApiOperation("Remove a cuisine")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cuisine removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    void remove(@ApiParam(name="cuisineId" , value = "Enter a valid cuisine ID", example ="1") Long id);

}
