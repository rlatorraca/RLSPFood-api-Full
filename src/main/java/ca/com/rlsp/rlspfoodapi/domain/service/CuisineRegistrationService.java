package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.CuisineNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CuisineRegistrationService {

    public static final String MSG_CUISINE_AS_CODE_IS_NOT_FOUND = "Cuisine of code %d %d not found into the Database";
    public static final String MSG_CUISINE_AS_CODE_IS_BEING_USED_AS_SECONDARY_KEY = "Cuisine of code %d %d cannot be removed, because that is being used as secondary key";

    @Autowired
    private CuisineRepository cuisineRepository;

    @Transactional
    public Cuisine save(Cuisine cuisine){
        return cuisineRepository.save(cuisine);
    }

    @Transactional
    public void remove(Long id) {
        try {
            cuisineRepository.deleteById(id);
            cuisineRepository.flush();
        } catch (EmptyResultDataAccessException e){
            /* Custom Exception */
            throw new CuisineNotFoundException(
                    String.format(MSG_CUISINE_AS_CODE_IS_NOT_FOUND, id)
            );


        } catch (DataIntegrityViolationException e){
            throw new EntityIsForeignKeyException(
                    String.format(MSG_CUISINE_AS_CODE_IS_BEING_USED_AS_SECONDARY_KEY, id)
            );
        }

    }

    public Cuisine findOrFail(Long id){
        return cuisineRepository.findById(id).orElseThrow(()-> new CuisineNotFoundException(String.format(MSG_CUISINE_AS_CODE_IS_NOT_FOUND, id)));
    }

    public List<Cuisine> listAll(){
        return cuisineRepository.findAll();
    }

    public Page<Cuisine> listAllPageable(Pageable pageable){
        return cuisineRepository.findAll(pageable);
    }

    public Optional<Cuisine> findById(Long id){
        try{
            return  cuisineRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CuisineNotFoundException(
                    String.format("Cuisine as code is %d not found into the Database", id)
            );
        }
    }
}
