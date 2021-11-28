package ca.com.rlsp.rlspfoodapi.jpa;

import ca.com.rlsp.rlspfoodapi.RlspfoodApiApplication;
import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;


public class DeleteCuisineMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(RlspfoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CuisineRepository serviceCuisine = applicationContext.getBean(CuisineRepository.class);

       Cuisine cuisine1 = new Cuisine();
       cuisine1.setName("Brazilian");

       Cuisine cuisine2 = new Cuisine();
       cuisine2.setName("Canadian");

       cuisine1 = serviceCuisine.save(cuisine1);
       cuisine2 = serviceCuisine.save(cuisine2);

       System.out.printf("%d - %s\n", cuisine1.getId(), cuisine1.getName());
       System.out.printf("%d - %s\n", cuisine2.getId(), cuisine2.getName());

       serviceCuisine.deleteById(cuisine1.getId());

       List<Cuisine> cuisines = serviceCuisine.findAll();

       for(Cuisine c : cuisines){
           System.out.println(c);
       }

    }
}
