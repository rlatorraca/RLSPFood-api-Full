package ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProvinceInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProvinceOutputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
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

@Api(tags = "Provinces")
public interface ProvinceControllerOpenApi {

    @ApiOperation(value = "List all provinces in JSON") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Provinces listed in JSON",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE
                            //schema = @Schema(implementation = ProvincesModelApi.class)
                    )
            )
    })
    CollectionModel<ProvinceOutputDto> listAllJson();
    //List<ProvinceOutputDto> listAllJson();

    @ApiOperation(value = "List all provinces in XML") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Provinces listed in XML",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_XML_VALUE
                            //schema = @Schema(implementation = ProvincesModelApi.class)
                    )
            )
    })
    List<Province> listAllXml();

    @ApiOperation(value = "Get a province by ID") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid province id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Province not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    ProvinceOutputDto findById(@ApiParam(value = "Province Id", example = "1", required = true)
                                                  Long provinceId);

    @ApiOperation(value = "Insert a province") // Costomize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Province created",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    ProvinceOutputDto save(@ApiParam(name = "body", value = "A DTO for inputs a resource of province")
                                              ProvinceInputDto provinceInputDTO);

    @ApiOperation(value = "Update data of a province by ID") // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Province updated",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Province not found",
                    content = @Content(schema = @Schema(implementation = ApiHandleProblemDetail.class)))
    })
    ProvinceOutputDto updateById(@ApiParam(value = "provinceId", example = "1", required = true)
                                                Long provinceId,
                                        @ApiParam(name = "body", value = "A DTO for inputs a resource of province")
                                                ProvinceInputDto provinceInputDTO);

    @ApiOperation("Remove a province")  // Customize method description on SwaggerUI
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Province removed",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            ),
            @ApiResponse(responseCode = "404", description = "Province not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiHandleProblemDetail.class))
            )
    })
    void remover(@ApiParam(value = "provinceId", example = "1", required = true)
                                    Long provinceId);

}
