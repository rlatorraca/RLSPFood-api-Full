package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.ProvinceNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceRegistrationService {


    public static final String MSG_PROVINCE_IS_USED_AS_SECONDARY_KEY = "Province of code %d cannot be removed, because that is being used as  secondary key";
    @Autowired
    private ProvinceRepository provinceRepository;


    @Transactional
    public Province save(Province province){
        return provinceRepository.save(province);
    }

    @Transactional
    public void remove(Long provinceId){
        try{
            provinceRepository.deleteById(provinceId);
            provinceRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new ProvinceNotFoundException(provinceId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityIsForeignKeyException(
                    String.format(MSG_PROVINCE_IS_USED_AS_SECONDARY_KEY, provinceId)
            );
        }
    }

    public List<Province> listAll(){
        return provinceRepository.findAll();
    }

    public Optional<Province> findById(Long id){
        try{
            return  provinceRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProvinceNotFoundException(
                    String.format("Province as code % dis not found into the Database", id)
            );
        }
    }

    public Province findOrFail(Long provinceId){
        return provinceRepository.findById(provinceId).orElseThrow(()-> new ProvinceNotFoundException(provinceId));
    }
}
