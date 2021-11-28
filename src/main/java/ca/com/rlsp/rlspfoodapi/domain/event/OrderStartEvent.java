package ca.com.rlsp.rlspfoodapi.domain.event;

import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStartEvent {

    private Order order;
}
