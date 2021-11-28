package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.CityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.repository.CityRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CityRegistrationService {

    public static final String MSG_CITY_AS_CODE_IS_NOT_FOUND_INTO_DATABASE = "City of code %d  %d not found into the Database";
    public static final String MSG_CITY_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY = "City of code %d cannot be removed, because that is being used as  secondary key";
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceRegistrationService provinceRegistrationService;

    @Transactional
    public City save(City city){
        Long provinceId = city.getProvince().getId();

        Province province = provinceRegistrationService.findOrFail(provinceId);
        city.setProvince(province);

        return cityRepository.save(city);
    }

    /*
    public City save(City city){
        Long provinceId = city.getProvince().getId();


        Province province = (Province) provinceRepository
                .findById(provinceId)
                .orElseThrow(
                        () -> new EntityNotFoundIntoDBException(
                                String.format(MSG_CITY_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, provinceId)
                        )
                );

        return cityRepository.save(city);
    }
    */
    @Transactional
    public void remove(Long id){
        try{
            cityRepository.deleteById(id);
            cityRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new CityNotFoundException(
                    String.format(MSG_CITY_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityIsForeignKeyException(
                    String.format(MSG_CITY_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY, id)
            );
        }
    }

    public List<City> listAll(){
        return cityRepository.findAll();
    }

    public Optional<City> findById(Long id){
        try{
            return  cityRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(
                    String.format(MSG_CITY_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        }
    }

    public City findOrFail(Long cityId){
        return cityRepository.findById(cityId).orElseThrow(()-> new CityNotFoundException(String.format(MSG_CITY_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, cityId)));
    }
}

