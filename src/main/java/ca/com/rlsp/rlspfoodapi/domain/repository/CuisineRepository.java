package ca.com.rlsp.rlspfoodapi.domain.repository;

import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuisineRepository extends CustomJpaRepository<Cuisine, Long> {


    List<Cuisine> findByNameContaining(String name);
    List<Cuisine> queryByNameStartingWith(String name);
    List<Cuisine> findByNameEndingWith(String name);
    Optional<Cuisine> findByName(String name);


    /*
        Usando Pagenle
     */
    // Page<Cuisine> findByNameContaining(String name, Pageable pageable);


}
