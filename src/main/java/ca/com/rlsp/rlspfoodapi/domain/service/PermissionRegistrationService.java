package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.CityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.PermissionNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Permission;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.repository.CityRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.PermissionRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionRegistrationService {

    public static final String MSG_PERMISSION_AS_CODE_IS_NOT_FOUND_INTO_DATABASE = "Permission of code %d  %d not found into the Database";

    @Autowired
    private PermissionRepository permissionRepository;


    @Transactional
    public Permission save(Permission permission){
        return permissionRepository.save(permission);
    }

    @Transactional
    public void remove(Long id){
        try{
            permissionRepository.deleteById(id);
            permissionRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new PermissionNotFoundException(
                    String.format(MSG_PERMISSION_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        }
    }

    public List<Permission> listAll(){
        return permissionRepository.findAll();
    }

    public Optional<Permission> findById(Long id){
        try{
            return  permissionRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(
                    String.format(MSG_PERMISSION_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        }
    }

    public Permission findOrFail(Long permissionId){
        return permissionRepository.findById(permissionId)
                .orElseThrow(
                        ()-> new CityNotFoundException(String.format(MSG_PERMISSION_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, permissionId)
                        ));
    }
}

