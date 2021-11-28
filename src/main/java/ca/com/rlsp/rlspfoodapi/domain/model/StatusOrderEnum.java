package ca.com.rlsp.rlspfoodapi.domain.model;

import lombok.Getter;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@Getter
public enum StatusOrderEnum {

    CREATED("Order Created"),
    CONFIRMED("Order Confirmed", CREATED),
    STARTED("Order Started", CONFIRMED),
    ON_THE_OVEN("Order on the oven", STARTED),
    READY("Order ready to delivery",ON_THE_OVEN),
    ON_THE_ROAD("Order on the road", READY),
    DELIVERED("Order Delivered", ON_THE_ROAD),
    CANCELED("Order Canceled", CREATED, CONFIRMED);

    private String description;
    private List<StatusOrderEnum> statusAuthorizedToChangeToNextStatusLevel;

    StatusOrderEnum(String description, StatusOrderEnum... statusAuthorizedToChangeToNextStatusLevel) {
        this.description = description;
        this.statusAuthorizedToChangeToNextStatusLevel = Arrays.asList(statusAuthorizedToChangeToNextStatusLevel);
    }

    public boolean authorizedModifyStatusTo(StatusOrderEnum newStatus){
        return newStatus.getStatusAuthorizedToChangeToNextStatusLevel().contains(this);
    }



}
