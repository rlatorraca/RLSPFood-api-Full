package ca.com.rlsp.rlspfoodapi.domain.model;

import ca.com.rlsp.rlspfoodapi.core.validation.GroupsBeanValidation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
@Table(name = "tbl_province")
public class Province {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(groups = GroupsBeanValidation.ProvinceIdValidation.class)
    private Long id;

    @NotBlank
    @Column(nullable = false, name = "province_name")
    private String name;

    //@NotBlank
    //@Column(nullable = false, name = "province_tax")
    @ManyToOne
    @JoinColumn(name = "taxprovince_id", nullable = false)
    private TaxProvince tax;



}
