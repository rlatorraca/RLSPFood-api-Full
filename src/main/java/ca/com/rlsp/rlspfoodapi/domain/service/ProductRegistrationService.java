package ca.com.rlsp.rlspfoodapi.domain.service;

import ca.com.rlsp.rlspfoodapi.domain.exception.CityNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.exception.ProductNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.model.City;
import ca.com.rlsp.rlspfoodapi.domain.model.Product;
import ca.com.rlsp.rlspfoodapi.domain.model.Province;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.CityRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProductRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRegistrationService {

    public static final String MSG_PRODUCT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE = "Product of code %d  on restaurant of code %d not found into the Database";
    public static final String MSG_PRODUCT_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY = "Product of code %d cannot be removed, because that is being used as  secondary key";

    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public Product save(Product product){
        return productRepository.save(product);
    }


    @Transactional
    public void remove(Long id){
        try{
            productRepository.deleteById(id);
            productRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new CityNotFoundException(
                    String.format(MSG_PRODUCT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntityIsForeignKeyException(
                    String.format(MSG_PRODUCT_CANNOT_BE_REMOVED_USED_AS_SECONDARY_KEY, id)
            );
        }
    }

    public List<Product> listAll(Restaurant restaurant){
        return productRepository.findProductsByRestaurant(restaurant);
    }
    public List<Product> listAllActives(Restaurant restaurant){
        return productRepository.findActivesProductsByRestaurant(restaurant);
    }

    public Optional<Product> findById(Long id){
        try{
            return  productRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(
                    String.format(MSG_PRODUCT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, id)
            );
        }
    }

    public Product findOrFail(Long restaurantId, Long productId){
        return productRepository.findById(restaurantId, productId).orElseThrow(()-> new ProductNotFoundException(String.format(MSG_PRODUCT_AS_CODE_IS_NOT_FOUND_INTO_DATABASE, productId, restaurantId)));
    }
}

