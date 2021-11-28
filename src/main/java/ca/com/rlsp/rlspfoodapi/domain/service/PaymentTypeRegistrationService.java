package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.PaymentTypeNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.PaymentType;
import ca.com.rlsp.rlspfoodapi.domain.repository.PaymentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PaymentTypeRegistrationService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO
            = "Payment Type of code %d cannot be removed,  because that is being used as secondary key";

    @Autowired
    private PaymentTypeRepository paymentTypeRepository;

    @Transactional
    public PaymentType save(PaymentType formaPagamento) {
        return paymentTypeRepository.save(formaPagamento);
    }

    @Transactional
    public void delete(Long paymentTypeId) {
        try {
            paymentTypeRepository.deleteById(paymentTypeId);
            paymentTypeRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new PaymentTypeNotFoundException(paymentTypeId);

        } catch (DataIntegrityViolationException e) {

            throw new EntityIsForeignKeyException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, paymentTypeId));
        }
    }

    public PaymentType findOrFail(Long paymentTypeId) {
        return paymentTypeRepository.findById(paymentTypeId)
                .orElseThrow(() -> new PaymentTypeNotFoundException(paymentTypeId));
    }
}
