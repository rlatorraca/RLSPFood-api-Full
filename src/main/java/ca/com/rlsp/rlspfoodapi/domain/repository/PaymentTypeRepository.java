package ca.com.rlsp.rlspfoodapi.domain.repository;

import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface PaymentTypeRepository extends CustomJpaRepository<PaymentType , Long> {

    @Query("select max(dateLastUpdate) from PaymentType ")
    OffsetDateTime getDateLasUpdate();

    @Query("select dateLastUpdate from PaymentType where id= :paymentTypeId")
    OffsetDateTime getDateLasUpdateById(Long paymentTypeId);

}
