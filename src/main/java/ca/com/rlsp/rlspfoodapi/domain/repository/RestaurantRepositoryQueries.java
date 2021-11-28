package ca.com.rlsp.rlspfoodapi.domain.repository;

import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {

    List<Restaurant> procurarRestauranteNasFaixas(String name, BigDecimal startFee, BigDecimal endFee);

    List<Restaurant> findRestaurantFreeDeliveryImpl(String name);
}
