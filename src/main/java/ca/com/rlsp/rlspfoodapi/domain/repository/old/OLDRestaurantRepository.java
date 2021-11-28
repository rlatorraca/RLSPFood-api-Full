package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;

import java.util.List;

public interface OLDRestaurantRepository {

    List<Restaurant> listAll();
    Restaurant findById(Long id);
    Restaurant save(Restaurant restaurant);
    void remove(Restaurant restaurant);
}
