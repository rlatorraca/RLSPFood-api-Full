package ca.com.rlsp.rlspfoodapi.infra.repository.specification;

import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.input.filter.OrderFilterInputDto;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;


public class OrderSpecifications {

    /*
        Implementendo  a Interface SPECIFICATION devemos implementar o metodo toPredicate visto abaixxo
            @Override
            public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
            }
        - No do metodo Static abaixo usamos lambada e faremos a mesma coisa
     */
    public static Specification<Order> gettingByFilter(OrderFilterInputDto orderFilter){
        //return new RestaurantFindFreeDeliverySpecification();
        return (root, query, criteriaBuilder) -> {
            if(Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("cuisine");
                //root.fetch("restaurant").fetch("address").fetch("city");
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if(orderFilter.getUserId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("user"), orderFilter.getUserId()));
            }

            if(orderFilter.getRestaurantId() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("restaurant"), orderFilter.getRestaurantId()));
            }

            if(orderFilter.getCreatedDateStart() != null ) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), orderFilter.getCreatedDateStart()));
            }

            if(orderFilter.getCreatedDateEnd() != null ) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), orderFilter.getCreatedDateEnd()));
            }
            // Transforma a Collection em um Array
            return criteriaBuilder.and(predicates.toArray((new Predicate[0])));
        };

    }



}
