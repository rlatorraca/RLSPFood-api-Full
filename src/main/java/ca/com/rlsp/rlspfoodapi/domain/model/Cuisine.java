package ca.com.rlsp.rlspfoodapi.domain.model;

import ca.com.rlsp.rlspfoodapi.core.validation.GroupsBeanValidation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


//@JsonRootName("gastronomy") // Mudar o nome do Ckasse em XML para outro nome
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbl_cuisine")
public class Cuisine {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(groups = {GroupsBeanValidation.CuisineIdValidation.class})
    private Long id;


    @NotBlank
    @JsonProperty("nome") // Muda o nome que vira no JSON quando consultado (diferentemente da Classe de Dominio)
    @Column(nullable = false, name="cuisine_name")
    private String name;

    //@JsonIgnore /// remove da Apresentacao da Classe de Dominio na resposta (no Json de resposta)
    @OneToMany(mappedBy = "cuisine")
    private List<Restaurant> restaurants = new ArrayList<>();


}
