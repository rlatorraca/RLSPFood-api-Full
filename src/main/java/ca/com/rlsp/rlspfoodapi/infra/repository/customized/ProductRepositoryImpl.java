package ca.com.rlsp.rlspfoodapi.infra.repository.customized;

import ca.com.rlsp.rlspfoodapi.api.v1.assembler.ProductPhotoModelAssembler;
import ca.com.rlsp.rlspfoodapi.domain.model.ProductPhoto;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProductRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*
    Implementacao de Repositorio de Restaurante Customizado
 */
@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ProductPhotoModelAssembler productPhotoModelAssembler;


    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto productPhoto) {
        return em.merge(productPhoto);
    }

    @Transactional
    @Override
    public void delete(ProductPhoto productPhoto) {
        em.remove(productPhoto);
    }

}
