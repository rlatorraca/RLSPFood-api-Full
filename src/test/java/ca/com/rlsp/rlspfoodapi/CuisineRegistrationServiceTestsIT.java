package ca.com.rlsp.rlspfoodapi;

/*
    TESTE DE INTEGRACAO
 */

import ca.com.rlsp.rlspfoodapi.domain.exception.CuisineNotFoundException;
import ca.com.rlsp.rlspfoodapi.domain.exception.EntityIsForeignKeyException;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.service.CuisineRegistrationService;
import com.fasterxml.jackson.annotation.OptBoolean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CuisineRegistrationServiceTestsIT {

    @Autowired
    private CuisineRegistrationService cuisineRegistrationService;

    @Test
    public void when_RegisteringCuisineCorrectly_ThenSuccess() {

        // CENARIO
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName("Testing Cuisine");
        // ACAO
        newCuisine = cuisineRegistrationService.save(newCuisine);

        //VALIDACAO
        assertThat(newCuisine).isNotNull();
        assertThat(newCuisine.getId()).isNotNull();

    }

    @Test
    public void when_RegisteringCuisineNoName_ThenFail() {

        // CENARIO
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName(null);

        // ACAO
        ConstraintViolationException exceptionxpected = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            cuisineRegistrationService.save(newCuisine);
        });  // Espera-se ocorrer um Exception

        //VALIDACAO
        assertThat(exceptionxpected).isNotNull();
        assertThat(exceptionxpected).isInstanceOf(ConstraintViolationException.class);

    }

    @Test
    public void when_TryingExcludeCuisineInUse_Then_Fail() {

        // CENARIO
        Optional<Cuisine> cuisine = cuisineRegistrationService.findById(1L);
        // ACAO
        EntityIsForeignKeyException exceptionxpected = Assertions.assertThrows(EntityIsForeignKeyException.class, () -> {
            cuisineRegistrationService.remove(cuisine.get().getId());
        });

        // VALIDACAO
            assertThat(exceptionxpected).isNotNull();

    }

    @Test
    public void when_TryingExcludeCuisineInexistent_Then_Fail() {

        /// CENARIO

        // ACAO
        CuisineNotFoundException exceptionxpected = Assertions.assertThrows(CuisineNotFoundException.class, () -> {
            cuisineRegistrationService.remove(1000L);
        });

        // VALIDACAO
        assertThat(exceptionxpected).isNotNull();
    }
}