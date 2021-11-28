package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.ProductModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.ProductInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.ProductInputUpdateStatusDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.ProductOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.RestaurantProductControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProductRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.ProductRegistrationService;
import ca.com.rlsp.rlspfoodapi.domain.service.RestaurantRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(value="/restaurants/{restaurantId}/products",  produces = {MediaType.APPLICATION_JSON_VALUE})
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products",  produces = {MediaType.APPLICATION_JSON_VALUE})
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

    private ProductRepository productRepository;
    private ProductRegistrationService productRegistrationService;
    private RestaurantRegistrationService restaurantRegistrationService;
    private ProductModelAssembler productModelAssembler;
    private ProductInputDisassembler productInputDisassembler;
    private BuildLinks buildLinks;

    public RestaurantProductController(ProductRepository productRepository,
                                       ProductRegistrationService productRegistrationService,
                                       RestaurantRegistrationService restaurantRegistrationService,
                                       ProductModelAssembler productModelAssembler,
                                       BuildLinks buildLinks,
                                       ProductInputDisassembler productInputDisassembler) {

        this.productRepository = productRepository;
        this.productRegistrationService = productRegistrationService;
        this.restaurantRegistrationService = restaurantRegistrationService;
        this.productModelAssembler = productModelAssembler;
        this.productInputDisassembler = productInputDisassembler;
        this.buildLinks = buildLinks;
    }

//    @GetMapping
//    public List<ProductOutputDto> listAll(@PathVariable("restaurantId") Long id ) {
//        Restaurant restaurant = restaurantRegistrationService.findOrFail(id);
//        List<ProductOutputDto> productOutputDtoList = productModelAssembler.fromControllerToOutputList(restaurant.getProducts());
//        return productOutputDtoList;
//    }

    @CheckSecurity.Restaurant.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping("/actives")
    //public List<ProductOutputDto> listAllActives(@PathVariable("restaurantId") Long id,
    public CollectionModel<ProductOutputDto> listAllActives(@PathVariable("restaurantId") Long id,
                                                            @RequestParam(required = false) Boolean justActiveProducts) {
        Restaurant restaurant = restaurantRegistrationService.findOrFail(id);
        List<Product> allProducts = null;

        if(justActiveProducts) {
            allProducts = productRegistrationService.listAllActives(restaurant);
        } else {
            allProducts = productRegistrationService.listAll(restaurant);
        }

        CollectionModel<ProductOutputDto> productOutputDtoList = productModelAssembler
                .toCollectionModel(allProducts)
                .removeLinks()
                .add(buildLinks.getLinkToProducts(id));
        //List<ProductOutputDto> productOutputDtoList = productOutputDtoList = productModelAssembler.fromControllerToOutputList(allProducts);

        return productOutputDtoList;
    }


    @CheckSecurity.Restaurant.hasPermissionToQuery // So pode acessar o metodo se estiver autenticado
    @Override
    @GetMapping("/{productId}")
    public ProductOutputDto findByRestaurantIdAndByProductId(@PathVariable Long restaurantId,
                                                             @PathVariable Long productId) {
        Product product = productRegistrationService.findOrFail(restaurantId, productId);

        return productModelAssembler.toModel(product);
        //return productModelAssembler.fromControllerToOutput(product);
    }

    /*
    @PutMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachPaymentType(@PathVariable Long paymentTypeId, @PathVariable Long restaurantId){
        restaurantRegistrationService.attachPaymentType(restaurantId,paymentTypeId);
    }
    */

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PutMapping("/{productId}")
    public ProductOutputDto update(@PathVariable Long restaurantId,
                                   @PathVariable Long productId,
                                   @RequestBody  @Valid ProductInputDto productInputDto) {
        return getProductOutputDto(restaurantId, productId, productInputDto);
    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PutMapping("/{productId}/status")
    public ProductOutputDto updateJustStatus(@PathVariable Long restaurantId,
                                   @PathVariable Long productId,
                                   @RequestBody  @Valid ProductInputUpdateStatusDto productInputDto) {
        return getProductOutputDto(restaurantId, productId, productInputDto);
    }

    private ProductOutputDto getProductOutputDto(Long restaurantId, Long productId, ProductInputUpdateStatusDto productInputDto) {
        try{
            Product currentProduct = productRegistrationService.findOrFail(restaurantId, productId);

            if(productInputDto instanceof ProductInputDto){
                productInputDisassembler.fromDTOtoProduct(productInputDto, currentProduct);
            } else {
                productInputDisassembler.fromDTOtoProductStatus(productInputDto, currentProduct);
            }

            currentProduct = productRegistrationService.save(currentProduct);

        return productModelAssembler.toModel(currentProduct);
        //return productModelAssembler.fromControllerToOutput(currentProduct);
        } catch ( EntityNotFoundException e ){
            throw new GenericBusinessException(e.getReason());
        }
    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductOutputDto save(@PathVariable Long restaurantId,
                                 @RequestBody @Valid ProductInputDto productInputDto) {
        Restaurant restaurant =  restaurantRegistrationService.findOrFail(restaurantId);

        Product product = productInputDisassembler.fromInputToController(productInputDto);

        product.setRestaurant(restaurant);

        product = productRegistrationService.save(product);

        return productModelAssembler.toModel(product);
        //return productModelAssembler.fromControllerToOutput(product);
    }

    @CheckSecurity.Restaurant.hasPermissionToManageRestaurant // So pode acessar o metodo se tive permissao de EDIT_CUISINE
    @DeleteMapping("/delete/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("productId") Long id) {
        productRegistrationService.remove(id);
    }
}
