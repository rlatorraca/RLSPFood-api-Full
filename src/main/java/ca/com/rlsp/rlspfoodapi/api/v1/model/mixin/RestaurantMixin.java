package ca.com.rlsp.rlspfoodapi.api.v1.model.mixin;

import ca.com.rlsp.rlspfoodapi.domain.model.Address;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.OffsetDateTime;
import java.util.List;

/*
    Faz c configuracao / conexao na Classe (RESTAURANT) em relacao as anotacoes do JACKSON (@Json*)
 */
public abstract class RestaurantMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cuisine cuisine;

    @JsonIgnore
    private Address address;

    @JsonIgnore
    private OffsetDateTime createdDate;

    //@JsonIgnore
    private OffsetDateTime dateLastUpdate;

    //@JsonIgnore
    private List<PaymentType> paymentTypeList ;

    @JsonIgnore
    private List<Product> products ;
}
