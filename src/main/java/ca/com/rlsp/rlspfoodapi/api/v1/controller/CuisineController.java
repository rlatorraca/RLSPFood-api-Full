package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.CuisineModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.CuisineControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.CuisineInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.CuisineXMLWrapper;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.CuisineInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.CuisineOutputDto;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.CuisineRegistrationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(value = "/cuisines", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequestMapping(value = "/v1/cuisines", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CuisineController implements CuisineControllerOpenApi {

    private  CuisineRepository cuisineRepository;
    private CuisineRegistrationService cuisineRegistrationService;
    private CuisineInputDisassembler cuisineInputDisassembler;
    private CuisineModelAssembler cuisineModelAssembler;

    // Para fazer a REPRESENTATIOn da PAGINACAO
    private PagedResourcesAssembler<Cuisine> pagedResourcesAssembler;

    public CuisineController(CuisineRepository cuisineRepository,
                             CuisineRegistrationService cuisineRegistrationService,
                             CuisineInputDisassembler cuisineInputDisassembler,
                             CuisineModelAssembler cuisineModelAssembler,
                             PagedResourcesAssembler<Cuisine> pagedResourcesAssembler) {

        this.cuisineRepository = cuisineRepository;
        this.cuisineRegistrationService = cuisineRegistrationService;
        this.cuisineInputDisassembler = cuisineInputDisassembler;
        this.cuisineModelAssembler = cuisineModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Override
    @GetMapping
    //public List<Cuisine> listAll(){
    //public List<CuisineOutputDto> listAll(){
    public CollectionModel<CuisineOutputDto> listAll(){
        //return cuisineRegistrationService.listAll();
        List<Cuisine> allCuisines= cuisineRegistrationService.listAll();
        return cuisineModelAssembler.toCollectionModel(allCuisines);
        //return cuisineModelAssembler.fromControllerToOutputList(allCuisines);
    }

    /*
        Usando PAGEABLE
     */

    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping(path = "/pageable-list")
    //public List<Cuisine> listAll(){
    public List<CuisineOutputDto> listAllPageableList(Pageable pageable){
        //return cuisineRegistrationService.listAll();
        Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);
        return cuisineModelAssembler.fromControllerToOutputList(allCuisinesPageable.getContent());
    }

    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @GetMapping(path = "/pageable")
    //public List<Cuisine> listAll(){
    //public Page<CuisineOutputDto> listAllPageable(@PageableDefault(size = 4) Pageable pageable){
    public PagedModel<CuisineOutputDto> listAllPageable(@PageableDefault(size = 4) Pageable pageable){
        //return cuisineRegistrationService.listAll();

        //Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);
        //List<CuisineOutputDto> cuisineOutputDtos = cuisineModelAssembler.toCollectionModel(allCuisinesPageable.getContent());

        //** Copia a lista de CUISINE para dentro de uma PAGE
        //Page<CuisineOutputDto> cuisineOutputDtosPages = new PageImpl<CuisineOutputDto>(cuisineOutputDtos, pageable, allCuisinesPageable.getTotalPages());

        //** Usando PagedModel (paginacao de representacao)
        Page<Cuisine> allCuisinesPageable= cuisineRegistrationService.listAllPageable(pageable);
        PagedModel<CuisineOutputDto> cuisinesPageModel = pagedResourcesAssembler
                .toModel(allCuisinesPageable,cuisineModelAssembler);

        return cuisinesPageModel;
    }

    /*
    @ResponseStatus(HttpStatus.OK) // Codigo de Resposta do Servidor quue sera enviado para essa requisaicao
    @GetMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> findBy1Id(@PathVariable("cuisineId") Long id){
        Optional<Cuisine> cuisine =  cuisineRepository.findById(id);

        //return ResponseEntity.ok(cuisine);
        if(cuisine.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(cuisine.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    */

    @CheckSecurity.Cuisine.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping("/{cuisineId}")
    //public Cuisine findBy1Id(@PathVariable("cuisineId") Long id){
    public CuisineOutputDto findBy1Id(@PathVariable("cuisineId") Long id){
        Cuisine cuisine = cuisineRegistrationService.findOrFail(id);

        //return cuisineRegistrationService.findOrFail(id);
        return cuisineModelAssembler.toModel(cuisine);
    }


    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CuisineXMLWrapper listAllXML(){
        return new CuisineXMLWrapper(cuisineRegistrationService.listAll());
    }

    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //public Cuisine save(@RequestBody @Valid Cuisine cuisine){
    public CuisineOutputDto save(@RequestBody @Valid CuisineInputDto cuisineInputDTO){

        Cuisine cuisine = cuisineInputDisassembler.fromInputToController(cuisineInputDTO);
        cuisine = cuisineRepository.save(cuisine);

        return cuisineModelAssembler.toModel(cuisine);
        //return cuisineModelAssembler.fromControllerToOutput(cuisine);
        //return cuisineRegistrationService.save(cuisine);
    }

    /*
    @PutMapping("/{cuisineId}")
    public ResponseEntity<?> updateById(@PathVariable("cuisineId") Long id, @RequestBody Cuisine cuisine){
        try {
            Optional<Cuisine> currentCuisine = cuisineRepository.findById(id);

            if (currentCuisine.isPresent()) {
                //currentCuisine.setName(cuisine.getName());
                BeanUtils.copyProperties(cuisine, currentCuisine.get(), "id"); // Copia (novo, antigo) objeto de cuisine
                Cuisine cuisineSaved = cuisineRepository.save(currentCuisine.get());

                return ResponseEntity.status(HttpStatus.OK).body(cuisineSaved);
            }
            return ResponseEntity.notFound().build();
        } catch (EntityNotFoundIntoDBException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    */

    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PutMapping("/{cuisineId}")
    //public Cuisine updateById(@PathVariable("cuisineId") Long id, @RequestBody @Valid Cuisine cuisine){
    public CuisineOutputDto updateById(@PathVariable("cuisineId") Long id, @RequestBody @Valid CuisineInputDto cuisineInputDTO){
        Cuisine currentCuisine = cuisineRegistrationService.findOrFail(id);

        cuisineInputDTO.setId(id);
        cuisineInputDisassembler.fromDTOtoCuisine(cuisineInputDTO, currentCuisine);
        //BeanUtils.copyProperties(cuisine, currentCuisine, "id"); // Copia (novo, antigo) objeto de cuisine

        currentCuisine = cuisineRegistrationService.save(currentCuisine);

        //return cuisineRegistrationService.save(currentCuisine);

        return cuisineModelAssembler.toModel(currentCuisine);
    }


    @CheckSecurity.Cuisine.hasPermissionToEdit // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("cuisineId") Long id) {
            cuisineRegistrationService.remove(id);
    }

    /*
    @DeleteMapping("/{cuisineId}")
    public ResponseEntity<Cuisine> remove(@PathVariable("cuisineId") Long id) {
        try {
            cuisineRegistrationService.remove(id);
            return ResponseEntity.noContent().build();

        } catch(EntityNotFoundIntoDBException e){
            return ResponseEntity.notFound().build();

        } catch (EntityIsForeignKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    */

    /*
    @DeleteMapping("/{cuisineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("cuisineId") Long id) {
        try{
            cuisineRegistrationService.remove(id);
        } catch (EntityNotFoundIntoDBException e){
            // Usando nova classe do SpringBoot 5 => customiza apenas o Status HTTP e a Mensagem
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,  e.getMessage());
            //throw new ServerWebInputException(e.getMessage());
        }
    }
    */



}
