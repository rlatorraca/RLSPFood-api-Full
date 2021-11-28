package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.Province;

import java.util.List;

public interface OLDProvinceRepository {

    List<Province> listAll();
    Province findById(Long id);
    Province save(Province province);
    void remove(Long id);
}
