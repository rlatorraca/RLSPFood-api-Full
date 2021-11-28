package ca.com.rlsp.rlspfoodapi.api.v1.controller;

import ca.com.rlsp.rlspfoodapi.api.v1.links.BuildLinks;
import ca.com.rlsp.rlspfoodapi.core.security.RlspFoodSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private BuildLinks buildLinks;

    @Autowired
    private RlspFoodSecurity rlspFoodSecurity;

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        if(rlspFoodSecurity.hasPermissionToQueryCuisines()){
            rootEntryPointModel.add(buildLinks.getLinkToCuisines("cuisines"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryOrders()) {
            rootEntryPointModel.add(buildLinks.getLinkToOrders("orders"));

        }

        if(rlspFoodSecurity.hasPermissionToQueryRestaurants()) {
            rootEntryPointModel.add(buildLinks.getLinkToRestaurants("restaurant"));

        }
        if(rlspFoodSecurity.hasPermissionToQueryUsersGroupsPermissions()){
            rootEntryPointModel.add(buildLinks.getLinkToGroups("groups"));
            rootEntryPointModel.add(buildLinks.getLinkToUsers("users"));
            rootEntryPointModel.add(buildLinks.getLinkToPermissions("permissions"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryCities()) {
            rootEntryPointModel.add(buildLinks.getLinkToCities("cities"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryPaymentTypes()) {
            rootEntryPointModel.add(buildLinks.getLinkToPaymentType("payment types"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryProvinces()) {
            rootEntryPointModel.add(buildLinks.getLinkToProvinces("provinces"));
        }

        if(rlspFoodSecurity.hasPermissionToQueryStatistics()) {
            rootEntryPointModel.add(buildLinks.getLinkToStatistics("statistics"));
        }

        return rootEntryPointModel;
    }


    private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel>{}

}
