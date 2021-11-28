package ca.com.rlsp.rlspfoodapi.api.v2.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.CuisineModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.controller.CityController;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.CuisineInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.CuisineXMLWrapper;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.CuisineControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.api.v2.assembler.CuisineModelAssemblerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.disassembler.CuisineInputDisassemblerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.input.CuisineInputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CuisineOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.openapi.controller.CuisineControllerOpenApiV2;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.CuisineRegistrationService;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j // Igaul a => private static final Logger log = LoggerFactory.getLogger(CuisineControllerV2.class);
@RestController
//@RequestMapping(value = "/cuisines", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequestMapping(value = "/v2/cuisines", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CuisineControllerV2 implements CuisineControllerOpenApiV2 {

    private  CuisineRepository cuisineRepository;
    private CuisineRegistrationService cuisineRegistrationService;
    private CuisineInputDisassemblerV2 cuisineInputDisassembler;
    private CuisineModelAssemblerV2 cuisineModelAssembler;

    // Para fazer a REPRESENTATION da PAGINACAO
    private PagedResourcesAssembler<Cuisine> pagedResourcesAssembler;

    private static final Logger logger = LoggerFactory.getLogger(CuisineControllerV2.class);

    public CuisineControllerV2(CuisineRepository cuisineRepository,
                               CuisineRegistrationService cuisineRegistrationService,
                               CuisineInputDisassemblerV2 cuisineInputDisassembler,
                               CuisineModelAssemblerV2 cuisineModelAssembler,
                               PagedResourcesAssembler<Cuisine> pagedResourcesAssembler) {

        this.cuisineRepository = cuisineRepository;
        this.cuisineRegistrationService = cuisineRegistrationService;
        this.cuisineInputDisassembler = cuisineInputDisassembler;
        this.cuisineModelAssembler = cuisineModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping
    //public List<Cuisine> listAll(){
    //public List<CuisineOutputDto> listAll(){
    public CollectionModel<CuisineOutputDtoV2> listAll(){
        //return cuisineRegistrationService.listAll();
        List<Cuisine> allCuisines= cuisineRegistrationService.listAll();
        return cuisineModelAssembler.toCollectionModel(allCuisines);
        //return cuisineModelAssembler.fromControllerToOutputList(allCuisines);
    }

    /*
        Usando PAGEABLE
     */

    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    //@PreAuthorize("isAuthenticated()") // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping(path = "/pageable-list")
    //public List<Cuisine> listAll(){
    public List<CuisineOutputDtoV2> listAllPageableList(Pageable pageable){
        //return cuisineRegistrationService.listAll();
        Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);


        return cuisineModelAssembler.fromControllerToOutputList(allCuisinesPageable.getContent());
    }


    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    //@PreAuthorize("isAuthenticated()") // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping(path = "/pageable")
    //public List<Cuisine> listAll(){
    //public Page<CuisineOutputDto> listAllPageable(@PageableDefault(size = 4) Pageable pageable){
    public PagedModel<CuisineOutputDtoV2> listAllPageable(@PageableDefault(size = 4) Pageable pageable){
        //return cuisineRegistrationService.listAll();

        //Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);
        //List<CuisineOutputDto> cuisineOutputDtos = cuisineModelAssembler.toCollectionModel(allCuisinesPageable.getContent());

        //** Copia a lista de CUISINE para dentro de uma PAGE
        //Page<CuisineOutputDto> cuisineOutputDtosPages = new PageImpl<CuisineOutputDto>(cuisineOutputDtos, pageable, allCuisinesPageable.getTotalPages());

        /**
         * Debug that show all Authorities/Permission existed into the Tokens
         *  System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
         */


        //** Usando PagedModel (paginacao de representacao)
        Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);
        PagedModel<CuisineOutputDtoV2> cuisinesPageModel = pagedResourcesAssembler
                .toModel(allCuisinesPageable,cuisineModelAssembler);

        log.info("Listing cuisines by {} pages by query ... ", pageable.getPageSize());

        return cuisinesPageModel;
    }

    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    //@PreAuthorize("isAuthenticated()")
    @Override
    @GetMapping("/{cuisineId}")
    //public Cuisine findBy1Id(@PathVariable("cuisineId") Long id){
    public CuisineOutputDtoV2 findById(@PathVariable("cuisineId") Long id){
        Cuisine cuisine = cuisineRegistrationService.findOrFail(id);

        //return cuisineRegistrationService.findOrFail(id);
        return cuisineModelAssembler.toModel(cuisine);
    }


    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CuisineXMLWrapper listAllXML(){
        return new CuisineXMLWrapper(cuisineRegistrationService.listAll());
    }


    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    //@PreAuthorize("hasAuthority('EDIT_CUISINE')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //public Cuisine save(@RequestBody @Valid Cuisine cuisine){
    public CuisineOutputDtoV2 save(@RequestBody @Valid CuisineInputDtoV2 cuisineInputDTO){

        Cuisine cuisine = cuisineInputDisassembler.fromInputToController(cuisineInputDTO);
        cuisine = cuisineRepository.save(cuisine);

        return cuisineModelAssembler.toModel(cuisine);
        //return cuisineModelAssembler.fromControllerToOutput(cuisine);
        //return cuisineRegistrationService.save(cuisine);
    }

    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    //@PreAuthorize("hasAuthority('EDIT_CUISINE')")
    @Override
    @PutMapping("/{cuisineId}")
    //public Cuisine updateById(@PathVariable("cuisineId") Long id, @RequestBody @Valid Cuisine cuisine){
    public CuisineOutputDtoV2 updateById(@PathVariable("cuisineId") Long id, @RequestBody @Valid CuisineInputDtoV2 cuisineInputDTO){
        Cuisine currentCuisine = cuisineRegistrationService.findOrFail(id);

        cuisineInputDTO.setId(id);
        cuisineInputDisassembler.fromDTOtoCuisine(cuisineInputDTO, currentCuisine);
        //BeanUtils.copyProperties(cuisine, currentCuisine, "id"); // Copia (novo, antigo) objeto de cuisine

        currentCuisine = cuisineRegistrationService.save(currentCuisine);

        //return cuisineRegistrationService.save(currentCuisine);

        return cuisineModelAssembler.toModel(currentCuisine);
    }


    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    //@PreAuthorize("hasAuthority('EDIT_CUISINE')")
    @Override
    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("cuisineId") Long id) {
            cuisineRegistrationService.remove(id);
    }


}
