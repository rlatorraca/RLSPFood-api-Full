package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;

import java.util.List;

public interface OLDCuisineRepository {

    List<Cuisine> listAll();
    List<Cuisine> findByName(String name);
    List<Cuisine> findContainInName(String name);
    Cuisine findById(Long id);
    Cuisine save(Cuisine cuisine);
    void remove(Long id);


}
