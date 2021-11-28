package ca.com.rlsp.rlspfoodapi.infra.repository.specification;

import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.infra.repository.specification.old.RestaurantFindFreeDeliverySpecification;
import ca.com.rlsp.rlspfoodapi.infra.repository.specification.old.RestaurantFindNameLikeSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecifications {

    /*
        Implementendo  a Interface SPECIFICATION devemos implementar o metodo toPredicate visto abaixo
            @Override
            public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
            }
        - No do metodo Static abaixo usamos lambada e fararemos a mesma coisa
     */
    public static Specification<Restaurant> findFreeDelivereySpec(){
        //return new RestaurantFindFreeDeliverySpecification();
        return ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("deliveryFee"), BigDecimal.ZERO));
    }

    /*
    Implementendo  a Interface SPECIFICATION devemos implementar o metodo toPredicate visto abaixo
            @Override
            public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%" );
            }
    - No do metodo Static abaixo usamos lambada e fararemos a mesma coisa
 */
    public static Specification<Restaurant> findNameLikeSpec(String name){
       //return new RestaurantFindNameLikeSpecification(name);
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }
}
