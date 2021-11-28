package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;

import java.util.List;

public interface OLDPaymentTypeRepository {

    List<PaymentType> listAll();
    PaymentType findById(Long id);
    PaymentType save(PaymentType paymentType);
    void remove(Long id);
}
