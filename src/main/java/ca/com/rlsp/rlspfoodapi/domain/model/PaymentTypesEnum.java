package ca.com.rlsp.rlspfoodapi.domain.model;


public enum PaymentTypesEnum {
    CASH (1, "Cash"),
    DEBITCARD (2, "Debit card"),
    CREDITCARD (3, "Credit card"),
    PAYPAL (4, "Pay Pal"),
    BITCOIN (5, "Bitcoin"),
    ETHERIUM (6, "Entherium");

    private final int id;
    private final String name;

    PaymentTypesEnum(int id, String name){
        this.id = id;
        this.name = name;
    }


}
