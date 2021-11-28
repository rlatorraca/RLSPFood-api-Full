package ca.com.rlsp.rlspfoodapi.infra.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.old.OLDRestaurantRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OLDRestaurantRepositoryImpl implements OLDRestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Restaurant> listAll(){
        TypedQuery<Restaurant> query = em.createQuery("from Restaurant", Restaurant.class);
        return query.getResultList();
    }

    @Override
    public Restaurant findById(Long id){
        return em.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant){
        return em.merge(restaurant);
    }

    @Override
    @Transactional
    public void remove(Restaurant restaurant){
        restaurant =  findById(restaurant.getId());
        em.remove(restaurant);
    }
}
