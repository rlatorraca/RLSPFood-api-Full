package ca.com.rlsp.rlspfoodapi.domain.model;

import ca.com.rlsp.rlspfoodapi.core.validation.DeliveryFeeAnnotation;
import ca.com.rlsp.rlspfoodapi.core.validation.GroupsBeanValidation;
import ca.com.rlsp.rlspfoodapi.core.validation.ValueZeroInsertDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValueZeroInsertDescription(valueField="deliveryFee", toCheckField="name", mandatoryDescription="Free Delivery")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tbl_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    //@NotNull // => NAO NULO
    // @NotEmpty // => NAO NULO e NAO VAZIO
    //@NotBlank // => NAO NULO,  NAO VAZIO e NAO EM  BRANCO
    //@NotBlank(groups = {GroupsBeanValidation.CuisineIdValidation.class})
    @NotBlank
    @Column(name="name_restaurant", length = 150, nullable = false)
    private String name;


    //@DecimalMin("1,99")
    //@PositiveOrZero(groups = {GroupsBeanValidation.CuisineIdValidation.class})
    @NotNull
    @DeliveryFeeAnnotation // Anotacao Composta Personalidade
    //@MulitpleDeliveryFeeAnnotation(number = 2)
    @PositiveOrZero(message = "{DeliveryFee.invalid}") // Busca no arquivo ValidationMessages.properties do Bean validation
        // (e nao no properties do SpringBoot), mas SpringBoot properties tem PREFERENCIA
    @Column(name="delivery_fee", nullable = false)
    private BigDecimal deliveryFee;

    /*
        Tudo que termina com ONE é EAGER Loading (fetch padrao)
     */
    //@JsonIgnore
    //@JsonIgnoreProperties({"hibernateLazyInitializer"}) // para ignorar a falta de serializacao para essa propriedade quando usando LAZY no ToOne
    //@NotNull(groups = {GroupsBeanValidation.CuisineIdValidation.class})

    @NotNull
        /*
            Ignora a propriedade nome vinda de CUISINE (Serializacao [OBject -> JSON])
             - Nao ignora Deserializaco (JSON -> Object) ou getting a informacao
         */
    /* @JsonIgnoreProperties(value = "nome", allowGetters = true) --> Na classe Mixin 8?
        /*
            faz a mesma coisa que @NotNull(groups = {GroupsBeanValidation.CuisineIdValidation.class})
         */
    @ConvertGroup(from = Default.class, to = GroupsBeanValidation.CuisineIdValidation.class)
    @Valid // ==> Valida as propriedades dentro da Classe Cuisine
    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @JsonIgnore
    @Embedded // Essa propriedade e do Tipo @Embadeble
    private Address address;

    private Boolean active = Boolean.TRUE;

    /*@JsonIgnore  -> Na classe MIXIN */
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdDate;

    /*@JsonIgnore  -> Na classe MIXIN */
    @UpdateTimestamp
    @Column(name = "date_last_update",nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateLastUpdate;

    /*
        Tudo que termina com MANY é LAZY Loading (fetch padrao)
     */
    /*@JsonIgnore  -> Na classe MIXIN */
    @ManyToMany
    @JoinTable(name = "tbl_restaurant_paymenttype",
               joinColumns = @JoinColumn(name = "restaurant_id"),
               inverseJoinColumns = @JoinColumn(name = "payment_type_id"))
    private Set<PaymentType> paymentTypeList = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "tbl_restaurant_user_manager ",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> managers = new HashSet<>();

    /*@JsonIgnore  -> Na classe MIXIN */
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products = new ArrayList<>();

    private Boolean opened = Boolean.FALSE;


    // OPEN a restaurant
    public void openRestaurant() {
        setOpened(true);
    }

    // CLOSE a restaurant
    public void closeRestaurant() {
        setOpened(false);
    }


    //  ACTIVE a Restaurant
    public void activate() {
        setActive(true);
    }

    // INACTIVE a Restaurant
    public void inactivate() {
        setActive(false);
    }

    // Links to Restaurant
    public boolean isOpened() {
        return this.opened;
    }

    public boolean isClosed() {
        return !isOpened();
    }

    public boolean isInactive() {
        return !isActive();
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean openPermitted() {
        return isActive() && isClosed();
    }

    public boolean activationPermitted() {
        return isInactive();
    }

    public boolean inactivationPermitted() {
        return isActive();
    }

    public boolean closePermitted() {
        return isOpened();
    }


    // Adiciona (vincula) uma nova forma de pagamento ao Restaurante
    public boolean attachPaymentType(PaymentType paymentType){
        return getPaymentTypeList().add(paymentType);
    }

    // Remove (desvincula) uma nova forma de pagamento ao Restaurante
    public boolean detachPaymentType(PaymentType paymentType){
        return getPaymentTypeList().remove(paymentType);
    }


    // Remove (desvincula) um Manager ao Restaurante
    public boolean detachManager(User user) {
        return getManagers().remove(user);
    }

    // Adiciona (vincula) um Manager ao Restaurante
    public boolean attachManager(User user) {
        return getManagers().add(user);
    }


    // Check is the Restaurant ACCEPT a Payment Type
    public boolean acceptPaymentType(PaymentType paymentType) {
        return getPaymentTypeList().contains(paymentType);
    }

    // Check is the Restaurant NOT ACCEPT a Payment Type
    public boolean notAcceptPaymentType(PaymentType paymentType) {
        return !acceptPaymentType(paymentType);
    }
}
