package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.openapi.controller.StatusOrderControllerOpenApi;
import ca.com.rlsp.rlspfoodapi.core.security.CheckSecurity;
import ca.com.rlsp.rlspfoodapi.domain.service.StatusOrderRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusOrderController implements StatusOrderControllerOpenApi {

    private StatusOrderRegistrationService statusOrderRegistrationService;

    public StatusOrderController(StatusOrderRegistrationService statusOrderRegistrationService) {
        this.statusOrderRegistrationService = statusOrderRegistrationService;
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-create")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> create(@PathVariable String orderCode) {
        statusOrderRegistrationService.toCreate(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@PathVariable String orderCode) {
        statusOrderRegistrationService.toConfirm(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-start")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> start(@PathVariable String orderCode) {
        statusOrderRegistrationService.toStart(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@PathVariable String orderCode) {
        statusOrderRegistrationService.toCancel(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-oven")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> oven(@PathVariable String orderCode) {
        statusOrderRegistrationService.toOnTheOven(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-ready")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ready(@PathVariable String orderCode) {
        statusOrderRegistrationService.toReady(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-road")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> road(@PathVariable String orderCode) {
        statusOrderRegistrationService.toOnTheRoad(orderCode);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Order.hasPermissionToManagerOrders
    @Override
    @PutMapping("/to-delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deliver(@PathVariable String orderCode) {
        statusOrderRegistrationService.toDelivered(orderCode);

        return ResponseEntity.noContent().build();
    }

}
