package ca.com.rlsp.rlspfoodapi.api.v2.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CityInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CityOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CityInputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CityOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;

import java.util.List;

@Api(tags = "Cities")
public interface CityControllerOpenApiV2 {



    @ApiOperation(value = "List all cities in XML", hidden = true) // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities listed in XML",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_XML_VALUE
                            //schema = @Schema(implementation = CityOutputDto.class)
                    )
            )
    })
    List<City> listAllXmlV2();


    @ApiOperation(value = "List all cities in JSON") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = CityOutputDto.class)
                    )
            )
    })
    CollectionModel<CityOutputDtoV2> listAllJson();



    @ApiOperation(value = "Get a City by ID") // Costomize method description on SwaggerUI
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
    CityOutputDtoV2 findById(@ApiParam(name = "cityId", value = "Enter a valid city ID", example = "1", required = true)
                                  Long cityId);

    @ApiOperation(value = "Insert a city") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = CityOutputDto.class)
                    )
            )
    })
    CityOutputDtoV2 save(@ApiParam(name = "body", value = "A DTO for inputs a resource of city")
                                 CityInputDtoV2 cityInputDTO);

    @ApiOperation(value = "Update data of a city by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = CityOutputDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    CityOutputDtoV2 updateById(@ApiParam(name="cityId" , value= "Enter a valid city ID", example = "1", required =true)
                                    Long id,
                                    @ApiParam(name = "body", value = "A DTO for inputs a resource of City")
                                            CityInputDtoV2 cityInputDTO);


    @ApiOperation("Remove a city")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "City removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = CityOutputDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    void delete(@ApiParam(name="cityId" , value = "Enter a valid city ID", example ="1" , required =true) Long id);

}
