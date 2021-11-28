package ca.com.rlsp.rlspfoodapi.infra.repository.customized;

import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;

import ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ca.com.rlsp.rlspfoodapi.infra.repository.specification.RestaurantSpecifications.findFreeDelivereySpec;
import static ca.com.rlsp.rlspfoodapi.infra.repository.specification.RestaurantSpecifications.findNameLikeSpec;

/*
    Implementacao de Repositorio de Restaurante Customizado
 */
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    @Lazy
    private ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> procurarRestauranteNasFaixas(String name,
                                      BigDecimal startFee, BigDecimal endFee){

        // Criando o Builder Para Criteria
        CriteriaBuilder builder = em.getCriteriaBuilder();

        // Criando um Criteria usando o Builder
        CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);

        // Cria um Root (Classe raiz/fonte)
        Root<Restaurant> root = criteria.from(Restaurant.class);

        // Adicionando um Predicate (filtro)

        /* Static
        Predicate namePredicate = builder.like(root.get("name"), "%"+ name + "%");
        Predicate startFeePredicate = builder.greaterThanOrEqualTo(root.get("deliveryFee"), startFee);
        Predicate endFeePredicate = builder.lessThanOrEqualTo(root.get("deliveryFee"), endFee);
        */

        // Dynamic
        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(name)){
            predicates.add(builder.like(root.get("name"), "%"+ name + "%"));
        }

        if(startFee != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), startFee ));
        }

        if(endFee != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), endFee));
        }

        // Passa o(s) predicate(s) criado acima
        //criteria.where(namePredicate, startFeePredicate, endFeePredicate);
        criteria.where(predicates.toArray(new Predicate[0]));

        // cria um TypedCQuery para devovler a consulta SQL
        TypedQuery<Restaurant> query = em.createQuery(criteria);
        return query.getResultList();

    }

    @Override
    public List<Restaurant> findRestaurantFreeDeliveryImpl(String name) {
        return restaurantRepository.findAll(findNameLikeSpec(name)
                .and(findFreeDelivereySpec()));
    }

}
