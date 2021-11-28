package ca.com.rlsp.rlspfoodapi.api.v1.model.mixin;

import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
    Faz c configuracao / conexao na Classe (CITI) em relacao as anotacoes do JACKSON (@Json*)
 */
public abstract class CityMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private Province province;
}
