package ca.com.rlsp.rlspfoodapi.infra.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProvinceRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.old.OLDProvinceRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OLDProvinceRepositoryImpl implements OLDProvinceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Province> listAll(){
        TypedQuery<Province> query = em.createQuery("from Province", Province.class);
        return query.getResultList();
    }

    @Override
    public Province findById(Long id){
        return em.find(Province.class, id);
    }

    @Override
    @Transactional
    public Province save(Province province){
        return em.merge(province);
    }

    @Override
    @Transactional
    public void remove(Long id){
        Province province =  findById(id);
        if(province == null){
            throw new EmptyResultDataAccessException(1);
        }
        em.remove(province);
    }
}
