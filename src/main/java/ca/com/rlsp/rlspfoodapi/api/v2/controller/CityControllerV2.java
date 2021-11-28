package ca.com.rlsp.rlspfoodapi.api.v2.controller;

import ca.com.rlsp.rlspfoodapi.api.uri.UriResourceHelper;
import ca.com.rlsp.rlspfoodapi.api.v1.controller.CityController;
import ca.com.rlsp.rlspfoodapi.api.v2.assembler.CityModelAssemblerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.disassembler.CityInputDisassemblerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CityInputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CityOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.openapi.controller.CityControllerOpenApiV2;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.exception.ProvinceNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.repository.CityRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.CityRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
//@RequestMapping(path = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
//@RequestMapping(path = "/cities", produces = "application/vnd.rlspfood.v1+json")
//@RequestMapping(path = "/cities", produces = {
//        RlspFoodVersionMediaType.V2_APPLICATION_JSON_VALUE
//})
public class CityControllerV2  implements CityControllerOpenApiV2 {

    private static final Logger logger = LoggerFactory.getLogger(CityControllerV2.class);

    private CityRegistrationService cityRegistrationService;
    private CityRepository cityRepository;
    private CityModelAssemblerV2 cityModelAssemblerV2;
    private CityInputDisassemblerV2 cityInputDisassemblerV2;

    public CityControllerV2(CityRegistrationService cityRegistrationService,
                            CityRepository cityRepository,
                            CityModelAssemblerV2 cityModelAssemblerV2,
                            CityInputDisassemblerV2 cityInputDisassemblerV2) {

        this.cityRegistrationService = cityRegistrationService;
        this.cityRepository = cityRepository;
        this.cityModelAssemblerV2 = cityModelAssemblerV2;
        this.cityInputDisassemblerV2 = cityInputDisassemblerV2;
    }


    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
    public List<City> listAllXmlV2() {
        return cityRepository.findAll();
    }


    @GetMapping
    public CollectionModel<CityOutputDtoV2> listAllJson() {
        logger.info("Listing Cities ... ");
        List<City> allCities = cityRepository.findAll();

        logger.info("Cities listed... ");


        return  cityModelAssemblerV2.toCollectionModel(allCities);
    }

    /* => implementacao antes do Spring Hateaos (sem CollectionModel<>)
    @GetMapping
    //public List<City> listAllJson(){
    public List<CityOutputDto> listAllJson() {
        List<City> allCities = cityRepository.findAll();


        return cityModelAssembler.fromControllerToOutputList(allCities);
        //return cityRepository.findAll();
    }
     */



    @GetMapping("/{cityId}")
    //public City findById(@PathVariable Long cityId) {
    public CityOutputDtoV2 findById( @PathVariable Long cityId) {
        City cidade = cityRegistrationService.findOrFail(cityId);

        //  return cityRegistrationService.findOrFail(cityId);

        CityOutputDtoV2 cityOutputDto  = cityModelAssemblerV2.toModel(cidade);

        return cityOutputDto;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //public City save(@RequestBody @Valid City city) {
    public CityOutputDtoV2 save(@RequestBody @Valid CityInputDtoV2 cityInputDTO) {
        try{
            City city = cityInputDisassemblerV2.fromInputToController(cityInputDTO);

            city = cityRegistrationService.save(city);

            CityOutputDtoV2 cityModel = cityModelAssemblerV2.fromControllerToOutput(city);

            /* Ajuda a criar uma URI usando as informacoes da requisicao atual */
            /* Adiciona a URI no cabecalho LOCATION da resposta*/
            UriResourceHelper.addUriInResponseHeader(cityModel.getIdCity());

            return cityModelAssemblerV2.toModel(city);
            //return cityModelAssembler.fromControllerToOutput(city);
            //return cityRegistrationService.save(city);
        } catch (EntityNotFoundException e ){
            throw new GenericBusinessException(e.getReason(), e);
        }
    }



    @PutMapping("/{cityId}")
    //public City updateById(@PathVariable("cityId") Long id, @RequestBody @Valid City city) {
    public CityOutputDtoV2 updateById(@PathVariable("cityId") Long id,
                                    @RequestBody @Valid CityInputDtoV2 cityInputDTO) {
        try{
            City currentCity = cityRegistrationService.findOrFail(id);

            cityInputDTO.setId(id);
            cityInputDisassemblerV2.fromDTOtoCity(cityInputDTO, currentCity);

            currentCity = cityRegistrationService.save(currentCity);

            return cityModelAssemblerV2.toModel(currentCity);
            //return cityModelAssembler.fromControllerToOutput(currentCity);
            //BeanUtils.copyProperties(city, currentCity, "id");
            //return cityRegistrationService.save(currentCity);
        } catch (ProvinceNotFoundException e ){
            throw new GenericBusinessException(e.getReason(), e);
        }
    }


    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("cityId") Long id) {
        cityRegistrationService.remove(id);
    }


}
