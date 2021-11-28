package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.OrderModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.assembler.OrderShortModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.OrderInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.OrderInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.filter.OrderFilterInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.OrderOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.OrderControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.data.PageWrapper;
import ca.com.rlsp.rlspfoodapi.core.data.PageableTranslator;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.GenericBusinessException;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import ca.com.rlsp.rlspfoodapi.domain.model.User;
import ca.com.rlsp.rlspfoodapi.domain.repository.OrderRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.IssueOfOrderRegistrationService;
import ca.com.rlsp.rlspfoodapi.infra.repository.specification.OrderSpecifications;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
//@RequestMapping(path = "/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v2/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {
    private OrderRepository orderRepository;
    private IssueOfOrderRegistrationService issueOfOrderRegistrationService;
    private OrderShortModelAssembler orderShortModelAssembler;
    private OrderModelAssembler orderModelAssembler;
    private OrderInputDisassembler orderInputDisassembler;
    private RlspFoodSecurity rlspFoodSecurity;

    private PagedResourcesAssembler<Order> pagedResourcesAssembler;
    public OrderController(OrderRepository orderRepository,
                           IssueOfOrderRegistrationService issueOfOrderRegistrationService,
                           OrderShortModelAssembler orderShortModelAssembler,
                           OrderModelAssembler orderModelAssembler,
                           OrderInputDisassembler orderInputDisassembler,
                           PagedResourcesAssembler<Order> pagedResourcesAssembler,
                           RlspFoodSecurity rlspFoodSecurity) {
        this.orderRepository = orderRepository;
        this.issueOfOrderRegistrationService = issueOfOrderRegistrationService;
        this.orderShortModelAssembler = orderShortModelAssembler;
        this.orderModelAssembler = orderModelAssembler;
        this.orderInputDisassembler = orderInputDisassembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.rlspFoodSecurity = rlspFoodSecurity;
    }

    /*
        Limitando os campos retornados pela API com @JsonFilter do Jackson
     */
//    @GetMapping
//    public MappingJacksonValue listAll(@RequestParam(required = false) String fields) {
//        List<Order> allOrders = orderRepository.findAll();
//        List<OrderShortOutputDto> allOrderOutputDto = orderShortModelAssembler.fromControllerToOutputList(allOrders);
//
//        MappingJacksonValue orderWrapper = new MappingJacksonValue(allOrderOutputDto);
//
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if(StringUtils.isNotBlank(fields)){
//            filterProvider.addFilter("orderFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields.split(",")));
//        }
//
//        orderWrapper.setFilters(filterProvider);
//
//        return orderWrapper;
//    }

    @Override
    @GetMapping("/filter-pageable")
    public PagedModel<OrderOutputDto> searchByFilterPageable(OrderFilterInputDto orderFilter,
                                                                  @PageableDefault(size = 2) Pageable pageable) {
        // traduz fields de pageable para Order (fields)
        Pageable pageableTranslated = translatePageable(pageable);

        Page<Order> allOrders = orderRepository
                .findAll(OrderSpecifications.gettingByFilter(orderFilter), pageableTranslated);

        allOrders = new PageWrapper<>(allOrders, pageable);

        return pagedResourcesAssembler.toModel(allOrders, orderModelAssembler);
    }

    /*
        Implementando PAGEABLE e SORTING dem ORDER (com filter)
     */

    /*
    @GetMapping("/filter-pageable")
    public Page<OrderOutputDto> searchByFilterPageable(OrderFilterInputDto orderFilter, @PageableDefault(size = 2) Pageable pageable) {
        // traduz fields de pageable para Order (fields)
        pageable = translatePageable(pageable);

        Page<Order> allOrders = orderRepository
                                .findAll(OrderSpecifications.gettingByFilter(orderFilter), pageable);
        List<OrderOutputDto> orderOutputDtoList = orderModelAssembler.fromControllerToOutputList(allOrders.getContent());

        Page<OrderOutputDto> orderOutputDtoPage = new PageImpl<>(orderOutputDtoList, pageable, allOrders.getTotalPages());

        return orderOutputDtoPage;
    }
    */

    /*
        Pesquisas complexas na API (by URL params)
     */

    @Override
    @GetMapping("/filter")
    public List<OrderOutputDto> searchByFilter(OrderFilterInputDto orderFilter) {
        List<Order> allOrders = orderRepository.findAll(OrderSpecifications.gettingByFilter(orderFilter));

        return orderModelAssembler.fromControllerToOutputList(allOrders);
    }

   //  Standard Way
    @GetMapping
    public List<OrderOutputDto> listAll() {
        List<Order> allOrders = orderRepository.findAll();

        return orderModelAssembler.fromControllerToOutputList(allOrders);
    }


    @CheckSecurity.Order.hasPermissionToGetOneOrder
    @Override
    @GetMapping("/{orderCode}")
    public OrderOutputDto find(@PathVariable String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);

        return orderModelAssembler.toModel(order);
    }

    @CheckSecurity.Order.hasPermissionToCreateOrder
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderOutputDto add(@Valid @RequestBody OrderInputDto orderInputDto) {
        try {
            Order newOrder = orderInputDisassembler.fromInputToController(orderInputDto);

            // TODO get AUTHENTICATED User
            newOrder.setUser(new User());
            newOrder.getUser().setId(rlspFoodSecurity.getUserId());

            newOrder = issueOfOrderRegistrationService.issue(newOrder);

            return orderModelAssembler.toModel(newOrder);
        } catch (EntityNotFoundException e) {
            throw new GenericBusinessException(e.getReason(), e);
        }
    }

    /*
        Faz a traducao de atributos que sao expostos no Pageable e nao estrao presentes dentro do Model (no caso Order)
        => Vai ser usado para o SORTING
     */
    private Pageable translatePageable(Pageable pageable) {
        // ** Poderiamos usar o Map.of (do Java)
        var mapping = ImmutableMap.of(
                "orderCode", "orderCode",
                "restaurant.name", "restaurant.name",
                "nameUser", "user.name",
                "afterTaxes", "afterTaxes"
        );

        return PageableTranslator.translate(pageable, mapping);
    }

}
