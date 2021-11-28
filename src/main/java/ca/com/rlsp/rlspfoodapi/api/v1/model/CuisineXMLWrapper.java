package ca.com.rlsp.rlspfoodapi.api.v1.model;



import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.NonNull;
import lombok.Data;

import java.util.List;

@JsonRootName("cuisines")
//@JacksonXmlRootElement(localName = "cuisines")
@Data
public class CuisineXMLWrapper {

    @JsonProperty("cuisine")
    //@JacksonXmlProperty(localName = "cozinha")
    @JacksonXmlElementWrapper(useWrapping = false) // Desabilita o Embrulho do elemento no XML
    @NonNull
    private List<Cuisine> cuisines;

}



