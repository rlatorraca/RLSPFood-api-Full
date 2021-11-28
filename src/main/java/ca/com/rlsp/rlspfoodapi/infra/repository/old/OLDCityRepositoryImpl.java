package ca.com.rlsp.rlspfoodapi.infra.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.CityRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.old.OLDCityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OLDCityRepositoryImpl implements OLDCityRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<City> listAll() {
        TypedQuery<City> query = em.createQuery("from City", City.class);
        return query.getResultList();
    }

    @Override
    public City findById(Long id) {
        City city = em.find(City.class, id);
        if(city == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return city;
    }

    @Override
    @Transactional
    public City save(City city) {
        return em.merge(city);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        City city =  findById(id);
        if(city == null){
            throw new EmptyResultDataAccessException(1);
        }
        em.remove(city);
    }
}
