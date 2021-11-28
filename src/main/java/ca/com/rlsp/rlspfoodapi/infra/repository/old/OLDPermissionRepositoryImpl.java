package ca.com.rlsp.rlspfoodapi.infra.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Permission;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.repository.PermissionRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.old.OLDPermissionRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.old.OLDProvinceRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OLDPermissionRepositoryImpl implements OLDPermissionRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Permission> listAll() {
        TypedQuery<Permission> query = em.createQuery("from Permission", Permission.class);
        return query.getResultList();
    }

    @Override
    public Permission findById(Long id) {
        Permission permission = em.find(Permission.class, id);
        if(permission == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return permission;
    }



    @Override
    public Permission save(Permission permission) {
       return em.merge(permission);
    }

    @Override
    public void remove(Long id) {
        Permission permission =  findById(id);
        if(permission == null){
            throw new EmptyResultDataAccessException(1);
        }
        em.remove(permission);
    }
}
