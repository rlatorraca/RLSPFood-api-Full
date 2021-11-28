package ca.com.rlsp.rlspfoodapi.domain.repository.old;

import ca.com.rlsp.rlspfoodapi.domain.model.Permission;

import java.util.List;

public interface OLDPermissionRepository {

    List<Permission> listAll();
    Permission findById(Long id);
    Permission save(Permission permission);
    void remove(Long id);
}
