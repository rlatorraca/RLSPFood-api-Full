package ca.com.rlsp.rlspfoodapi.infra.service.query;

import ca.com.rlsp.rlspfoodapi.domain.filter.DailySalesFilter;
import ca.com.rlsp.rlspfoodapi.domain.model.Order;
import ca.com.rlsp.rlspfoodapi.domain.model.StatusOrderEnum;
import ca.com.rlsp.rlspfoodapi.domain.model.statistics.DailySales;
import ca.com.rlsp.rlspfoodapi.domain.service.DailySalesQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class DailySalesQueryServiceImpl implements DailySalesQueryService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DailySales> queryDailySales(DailySalesFilter filter, String timeOffSet) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        var query = builder.createQuery(DailySales.class);
        var root = query.from(Order.class);

        var functionTimeZoneCreatedDate = builder.function("convert_tz", Date.class,
                                                                root.get("createdDate"),
                                                                builder.literal("+00:00"),
                                                                builder.literal(timeOffSet));

        var functionToBuildCreatedDate = builder.function("date",
                                                                          Date.class,
                                                                          functionTimeZoneCreatedDate);

        System.out.println("functionToBuildCreatedDate : "+ functionToBuildCreatedDate.toString());
        var selection = builder.construct(DailySales.class,
                                                          functionToBuildCreatedDate,
                                                          builder.count(root.get("id")),
                                                          builder.sum(root.get("afterTax")));


        var predicates = new ArrayList<Predicate>();

        if(filter.getRestaurantId() != null){
           predicates.add(
                   builder.equal(
                           root.get("restaurant"),
                           filter.getRestaurantId()
                   )
           );
        }


        if(filter.getCreatedDateStart() != null){
            predicates.add(
                    builder.greaterThanOrEqualTo(
                            root.get("createdDate"),
                            filter.getCreatedDateStart()
                    )
            );
        }

        if(filter.getCreatedDateEnd() != null){
            predicates.add(
                    builder.lessThanOrEqualTo(
                            root.get("createdDate"),
                            filter.getCreatedDateEnd()
                    )
            );
        }


        predicates.add(root.get("status").in(StatusOrderEnum.CREATED, StatusOrderEnum.CANCELED).not());

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionToBuildCreatedDate);

        return em.createQuery(query).getResultList();
    }
}
