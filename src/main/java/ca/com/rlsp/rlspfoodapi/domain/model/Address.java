package ca.com.rlsp.rlspfoodapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable // Classe é incorporavel por outra Entidade (tem a capacidade de ser parte de outra entidade esera refletida em outra tabela)
public class Address {

    @Column(name = "address_postalcode")
    private String postalcode;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_district")
    private String district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;



}
