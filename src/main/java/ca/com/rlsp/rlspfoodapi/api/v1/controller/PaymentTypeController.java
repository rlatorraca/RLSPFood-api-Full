package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.PaymentTypeModelAssembler;
import ca.com.rlsp.rlspfoodapi.api.v1.disassembler.PaymentTypeInputDisassembler;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.PaymentTypeInputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.PaymentTypeOutputDto;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.PaymentTypeControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import ca.com.rlsp.rlspfoodapi.domain.repository.PaymentTypeRepository;
import ca.com.rlsp.rlspfoodapi.domain.service.PaymentTypeRegistrationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
//@RequestMapping(path = "/paymenttypes", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/paymenttypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentTypeController implements PaymentTypeControllerOpenApi {

    private PaymentTypeRepository paymentTypeRepository;
    private PaymentTypeRegistrationService paymentTypeRegistrationService;
    private PaymentTypeModelAssembler paymentTypeModelAssembler;
    private PaymentTypeInputDisassembler paymentTypeInputDisassembler;

    public PaymentTypeController(PaymentTypeRepository paymentTypeRepository,
                                 PaymentTypeRegistrationService paymentTypeRegistrationService,
                                 PaymentTypeModelAssembler paymentTypeModelAssembler,
                                 PaymentTypeInputDisassembler paymentTypeInputDisassembler) {
        this.paymentTypeRepository = paymentTypeRepository;
        this.paymentTypeRegistrationService = paymentTypeRegistrationService;
        this.paymentTypeModelAssembler = paymentTypeModelAssembler;
        this.paymentTypeInputDisassembler = paymentTypeInputDisassembler;
    }


    /*
        => GET All payment types returning Cache-Control to the Client App
        => Example of DEEP ETag
     */
    /*
    @GetMapping
    public ResponseEntity<List<PaymentTypeOutputDto>> listAll(ServletWebRequest request) {

        // Para usar o Deep ETags desabilitamos o Filter Shalllow ETag
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        // Gera-se o Deep ETag
        String deepETag = "0"; // para o caso nao haver a ETAg setado
        OffsetDateTime dateLastUpdate = paymentTypeRepository.getDateLasUpdate();

        if(dateLastUpdate != null){
            // "deepETag" => sera a data da ultima modificacao feita
            deepETag = String.valueOf(dateLastUpdate.toEpochSecond());
        }


         //   Agui temo condicoes de testar se precisaremo retornar os novos, em caso, de recebimento
         //  de if-non-matchsera executado o codigo apos esse if. Caso contrario, retorna null.
         //   - A primeira vez sera false, essa instrucao pq nao esta em cache ainda

         if(request.checkNotModified(deepETag)) {
             return null;
         }

        List<PaymentType> todasFormasPagamentos = paymentTypeRepository.findAll();

        List<PaymentTypeOutputDto> paymentTypeOutputDtos = paymentTypeModelAssembler
                .fromControllerToOutputList(todasFormasPagamentos);
        return ResponseEntity
                    .ok()
                    .cacheControl(CacheControl
                                    .maxAge(10, TimeUnit.SECONDS) // Cache to Client (10 s in this case)
                                    .cachePublic() // Permite TODOS tipos de Cache (local e compartilhado)
                    )
                    .eTag(deepETag)
                    .body(paymentTypeOutputDtos);
    }

    /*
        => GET All payment types returning Cache-Control to the Client App
        => Example of Shallow ETag
     */

    @CheckSecurity.PaymentType.hasPermissionToQuery
    @Override
    @GetMapping
    //public ResponseEntity<List<PaymentTypeOutputDto>> listAll(ServletWebRequest request) {
    public ResponseEntity<CollectionModel<PaymentTypeOutputDto>> listAll(ServletWebRequest request) {

        List<PaymentType> allPaymentTypes = paymentTypeRepository.findAll();

        //List<PaymentTypeOutputDto> paymentTypeOutputDtos = paymentTypeModelAssembler
        //        .fromControllerToOutputList(allPaymentTypes);

        CollectionModel<PaymentTypeOutputDto> paymentTypeOutputDtos = paymentTypeModelAssembler
                .toCollectionModel(allPaymentTypes);
        return ResponseEntity
                .ok()
                .cacheControl(
                        //CacheControl.noCache()) // SEMPRE fara a validacao do Cache (como se sempre estivesse no estado STALE)
                        //CacheControl.noSore()) // SEM Cache (nao armazena qualquer tipo de Cache)
                        CacheControl
                                .maxAge(10, TimeUnit.SECONDS) // Cache to Client (10 s in this case)
                                .cachePrivate() // Nao permit cache em Proxy Reverso (apenas local, na maquina do cliente)
                                //.cachePublic() // Permite TODOS tipos de Cache (local e compartilhado)

                )
                .body(paymentTypeOutputDtos);


    }

    /*
         => GET 1 payment types returning Cache-Control to the Client App
          - Deep ETags
    */

    @CheckSecurity.PaymentType.hasPermissionToQuery
    @Override
    @GetMapping("/{paymentTypeId}")
    public ResponseEntity<PaymentTypeOutputDto> findById(@PathVariable Long paymentTypeId, ServletWebRequest request) {

        // Para usar o Deep ETags desabilitamos o Filter Shalllow ETag
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        // Gera-se o Deep ETag
        String deepETag = "0"; // para o caso nao haver a ETAg setado
        OffsetDateTime dateLastUpdate = paymentTypeRepository.getDateLasUpdateById(paymentTypeId);

        if(dateLastUpdate != null){
            // "deepETag" => sera a data da ultima modificacao feita
            deepETag = String.valueOf(dateLastUpdate.toEpochSecond());
        }


        //   Agui temo condicoes de testar se precisaremo retornar os novos, em caso, de recebimento
        //  de if-non-matchsera executado o codigo apos esse if. Caso contrario, retorna null.
        //   - A primeira vez sera false, essa instrucao pq nao esta em cache ainda

        if(request.checkNotModified(deepETag)) {
            return null;
        }

        PaymentType formaPagamento = paymentTypeRegistrationService.findOrFail(paymentTypeId);

        PaymentTypeOutputDto paymentTypeOutputDto = paymentTypeModelAssembler.fromControllerToOutput(formaPagamento);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .eTag(deepETag)
                .body(paymentTypeOutputDto);
    }

    /*
         => GET 1 payment types returning Cache-Control to the Client App
          - Shallow ETags
    */
    /*
    @GetMapping("/{paymentTypeId}")
    public ResponseEntity<PaymentTypeOutputDto> findById(@PathVariable Long paymentTypeId) {
        PaymentType formaPagamento = paymentTypeRegistrationService.findOrFail(paymentTypeId);

        PaymentTypeOutputDto paymentTypeOutputDto = paymentTypeModelAssembler.fromControllerToOutput(formaPagamento);

        return ResponseEntity
                .ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentTypeOutputDto);
    }
     */


    /* => Regular GET
    @GetMapping
    public List<PaymentTypeOutputDto> listAll() {
        List<PaymentType> todasFormasPagamentos = paymentTypeRepository.findAll();

        return paymentTypeModelAssembler.fromControllerToOutputList(todasFormasPagamentos);
    }
     */

    /* => Regular GET 1 payment Type
    @GetMapping("/{paymentTypeId}")
    public PaymentTypeOutputDto (@PathVariable Long paymentTypeId) {
        PaymentType formaPagamento = paymentTypeRegistrationService.findOrFail(paymentTypeId);

        return paymentTypeModelAssembler.fromControllerToOutput(formaPagamento);
    }

     */

    @CheckSecurity.PaymentType.hasPermissionToEdit
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentTypeOutputDto save(@RequestBody @Valid PaymentTypeInputDto paymentTypeInputDto) {
        PaymentType formaPagamento = paymentTypeInputDisassembler.fromInputToController(paymentTypeInputDto);

        formaPagamento = paymentTypeRegistrationService.save(formaPagamento);

        return paymentTypeModelAssembler.fromControllerToOutput(formaPagamento);
    }

    @CheckSecurity.PaymentType.hasPermissionToEdit
    @Override
    @PutMapping("/{paymentTypeId}")
    public PaymentTypeOutputDto update(@PathVariable Long paymentTypeId,
                                         @RequestBody @Valid PaymentTypeInputDto paymentTypeInputDto) {
        PaymentType currentPaymentType = paymentTypeRegistrationService.findOrFail(paymentTypeId);

        paymentTypeInputDto.setId(paymentTypeId);
        paymentTypeInputDisassembler.fromDTOtoPaymentType(paymentTypeInputDto, currentPaymentType);

        currentPaymentType = paymentTypeRegistrationService.save(currentPaymentType);

        return paymentTypeModelAssembler.fromControllerToOutput(currentPaymentType);
    }

    @DeleteMapping("/{paymentTypeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long formaPagamentoId) {
        paymentTypeRegistrationService.delete(formaPagamentoId);
    }
}
