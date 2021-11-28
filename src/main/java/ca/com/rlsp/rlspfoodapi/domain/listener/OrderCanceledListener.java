package ca.com.rlsp.rlspfoodapi.domain.listener;

import ca.com.rlsp.rlspfoodapi.domain.event.OrderCancelEvent;
import ca.com.rlsp.rlspfoodapi.domain.event.OrderConfirmedEvent;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import ca.com.rlsp.rlspfoodapi.domain.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCanceledListener {

    @Autowired
    private SendEmailService sendEmailService;
    /*
        Sempre OrderConfirmedEvent disparado sera ouvido por essa classe e efetuara o codigo desejado
     */
    //@EventListener // => Evento disparado ANTES do commit da transacao
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // => Evento disparado depois do Commit na transacao
    public void whenOrderCanceled(OrderCancelEvent event){
        Order order = event.getOrder();

        var message = SendEmailService.Message.builder()
                .subject(order.getRestaurant().getName() + "- Order Canceled")
                .body("order-canceled.html")
                .templateAttribute("order", order)
                .destination(order.getUser().getEmail())
                .build();

        sendEmailService.send(message);

    }
}
