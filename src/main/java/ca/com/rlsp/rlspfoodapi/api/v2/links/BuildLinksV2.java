package ca.com.rlsp.rlspfoodapi.api.v2.links;

import ca.com.rlsp.rlspfoodapi.api.v1.controller.CuisineController;
import ca.com.rlsp.rlspfoodapi.api.v2.controller.CityControllerV2;
import ca.com.rlsp.rlspfoodapi.api.v2.controller.CuisineControllerV2;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuildLinksV2 {


    public Link getLinkToCities(Long cityId, String relation) {
        return linkTo(methodOn(CityControllerV2.class)
                .findById(cityId)).withRel(relation);
    }

    public Link getLinkToCities(Long cityId) {
        return getLinkToCities(cityId, IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCities(String relation) {
        return linkTo(CityControllerV2.class).withRel(relation);
    }

    public Link getLinkToCities() {
        return getLinkToCities(IanaLinkRelations.SELF.value());
    }

    public Link getLinkToCuisines(String relation) {
        return linkTo(CuisineControllerV2.class).withRel(relation);
    }

    public Link getLinkToCuisines() {
        return getLinkToCuisines(IanaLinkRelations.SELF.value());
    }





}
