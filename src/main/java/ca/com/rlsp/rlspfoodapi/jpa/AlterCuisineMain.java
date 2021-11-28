package ca.com.rlsp.rlspfoodapi.jpa;

import ca.com.rlsp.rlspfoodapi.RlspfoodApiApplication;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;


public class AlterCuisineMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(RlspfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CuisineRepository cuisineRepository = applicationContext.getBean(CuisineRepository.class);

       Cuisine cuisine1 = new Cuisine();
       cuisine1.setName("Brazilian");

       Cuisine cuisine2 = new Cuisine();
       cuisine2.setName("Canadian");

       cuisine1 = cuisineRepository.save(cuisine1);
       cuisine2 = cuisineRepository.save(cuisine2);

       System.out.printf("%d - %s\n", cuisine1.getId(), cuisine1.getName());
       System.out.printf("%d - %s\n", cuisine2.getId(), cuisine2.getName());

       cuisine1.setId(6L);
       cuisine1.setName("Pantaneira");

        cuisineRepository.save(cuisine1);
       System.out.printf("%d - %s\n", cuisine1.getId(), cuisine1.getName());

    }
}
