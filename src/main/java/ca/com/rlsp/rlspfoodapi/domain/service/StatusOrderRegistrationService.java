package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import ca.com.rlsp.rlspfoodapi.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatusOrderRegistrationService {

    @Autowired
    private IssueOfOrderRegistrationService issueOfOrderRegistrationService;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public void toConfirm(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.confirm();

        /*
            Mesmo salvando a ORDER por meio do SERVICE usamos o REPOSITORY oara que o Spring JPA funcione os EVENTOS
            disparados pelo Spring JPA
            - Disprando assim os eventos que estao na fila
         */
        orderRepository.save(order);

    }
    @Transactional
    public void toCreate(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.cancel();

        orderRepository.save(order);
    }

    @Transactional
    public void toCancel(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.cancel();

        orderRepository.save(order);
    }

    @Transactional
    public void toStart(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.start();

        orderRepository.save(order);
    }

    @Transactional
    public void toOnTheOven(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.onTheOven();

        orderRepository.save(order);
    }

    @Transactional
    public void toReady(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.ready();

        orderRepository.save(order);
    }

    @Transactional
    public void toOnTheRoad(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.onTheRoad();

        orderRepository.save(order);
    }

    @Transactional
    public void toDelivered(String orderCode) {
        Order order = issueOfOrderRegistrationService.findOrFail(orderCode);
        order.delivered();

        orderRepository.save(order);
    }
}
