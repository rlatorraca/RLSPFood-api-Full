package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.City;

import java.util.List;

public interface OLDCityRepository {

    List<City> listAll();
    City findById(Long id);
    City save(City city);
    void remove(Long id);
}
