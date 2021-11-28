package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.RestaurantInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantBasicsOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantJustNamesOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.RestaurantOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.model.RestaurantGenericModelOpenApi;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "Restaurants")
public interface RestaurantControllerOpenApi {

    @ApiOperation(value = "Restaurants list", response = RestaurantGenericModelOpenApi.class)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invalid restaurant id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "List of restaurants by justName or summary",
                    allowableValues = "justName",
                    name = "summary",
                    paramType = "query",
                    type = "string",
                    required = false
            )
    })
    //List<RestaurantOutputDto> listAll() ;
    CollectionModel<RestaurantOutputDto> listAll() ;

    @ApiIgnore
    @ApiOperation(value = "Restaurants list", hidden = true) // esconde na documentaoca
    //@JsonView(RestaurantView.Summary.class)
    //List<RestaurantOutputDto> listAllSummary() ;
    CollectionModel<RestaurantBasicsOutputDto> listAllSummary() ;

    @ApiIgnore
    @ApiOperation(value = "Restaurants list", hidden = true) // esconde na documentaoca
    //@JsonView(RestaurantView.SummaryJustName.class)
    //List<RestaurantOutputDto> listAllJustNames() ;
    CollectionModel<RestaurantJustNamesOutputDto> listAllJustNames() ;

    @ApiOperation(value = "Get a Restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid restaurant id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    RestaurantOutputDto findById(@ApiParam(name = "restaurantId", example = "1", required = true)
                                                     Long id) ;


    @ApiOperation(value = "Insert a restaurant") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            )
    })
    RestaurantOutputDto save(@ApiParam(name = "body", value = "A DTO for inputs a resource of restaurant")
                                                RestaurantInputDto restaurantInputDTO) ;


    @ApiOperation(value = "Update data of a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurant updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    RestaurantOutputDto updateById( @ApiParam(name="restaurantId" , value= "Enter a valid cuisine ID", example = "1", required =true)
                                             Long id,

                                           @ApiParam(name = "body", value = "A DTO for inputs a resource of restaurant")
                                           RestaurantInputDto restaurantInputDTO) ;

    RestaurantOutputDto updateByIdPatch( Long id,
                                               Map<String, Object> restaurantFields,
                                               HttpServletRequest request);


    @ApiOperation(value = "Activate data of a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    //void activate(@ApiParam(name="restaurantId" , value= "Enter a valid restaurant ID", example = "1", required =true)
    //                            Long id);
    ResponseEntity<Void>  activate(@ApiParam(name="restaurantId" , value= "Enter a valid restaurant ID", example = "1", required =true)
                                 Long id);

    @ApiOperation(value = "Multiples activation of a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "estaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    //void activateMultiplesRestaurants( @ApiParam(name = "field", value = "Restaurants Ids", required = true)
    //                                                      List<Long> restaurantsIds) ;
    ResponseEntity<Void>  activateMultiplesRestaurants( @ApiParam(name = "field", value = "Restaurants Ids", required = true)
                                                          List<Long> restaurantsIds) ;

    @ApiOperation(value = "Multiples deactivation of a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    //void deactivateMultiplesRestaurants( @ApiParam(name = "field", value = "Restaurants Ids", required = true)
    //                                                        List<Long> restaurantsIds);
    ResponseEntity<Void>  deactivateMultiplesRestaurants( @ApiParam(name = "field", value = "Restaurants Ids", required = true)
                                                                List<Long> restaurantsIds);

    @ApiOperation(value = "Open a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    //void openRestaurant( Long restaurantId) ;
    ResponseEntity<Void>  openRestaurant( Long restaurantId) ;

    @ApiOperation(value = "Close a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
//    void closeRestaurant( @ApiParam(value = "Restaurant Id", example = "1", required = true)
//                                             Long restaurantId) ;
    ResponseEntity<Void>  closeRestaurant( @ApiParam(value = "Restaurant Id", example = "1", required = true)
                                             Long restaurantId) ;


    @ApiOperation(value = "Inactivate data of a restaurant by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurant successfully activate",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ApiHandleProblemDetail.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
//    void inactivate( @ApiParam(value = "Restaurant Id", example = "1", required = true)
//                                        Long id);
    ResponseEntity<Void> inactivate(@ApiParam(value = "Restaurant Id", example = "1", required = true)
                                        Long id);
}
