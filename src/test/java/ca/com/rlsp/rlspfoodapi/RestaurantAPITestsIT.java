package ca.com.rlsp.rlspfoodapi;

import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.model.Restaurant;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import ca.com.rlsp.rlspfoodapi.domain.repository.RestaurantRepository;
import ca.com.rlsp.rlspfoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.com.rlsp.rlspfoodapi.util.ResourcesUtils;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Levanta um Servidor Web para testar
@TestPropertySource("/application_test.properties") // Para fazer as mudancas necessarias para o DB de tests
public class RestaurantAPITestsIT {

    public static final String RESOURCE_NO_FOUND = "Resource not found";
    @LocalServerPort
    // Como estamos usando RANDOM_PORT (porta aleatoria) temos que pegar essa porta para entao fazermos a conexao com o servidor WEB mock
    private int randomPort;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;


    private static final String BUSINESS_RULE_VIOLATED = "Business rules was violated";

    private static final String INVALID_DATA = "Invalid data";

    private static final int RESTAURANT_ID_INEXISTENT = 100000;

    private String jsonRestaurantCorrect;
    private String jsonRestaurantNoDeliveryFee;
    private String jsonRestaurantNoCuisine;
    private String jsonRestaurantCuisineInexistent;
    private int totalRegisteredRestaurants;

    private Restaurant barbacueRestaurant;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = randomPort;
        RestAssured.basePath = "/restaurants";

        jsonRestaurantCorrect = ResourcesUtils.getContentFromResource(
                "/json/restaurant/correct/oneRestaurant.json");

        jsonRestaurantNoDeliveryFee = ResourcesUtils.getContentFromResource(
                "/json/restaurant/incorrect/oneRestaurantNoDeliveryFee.json");

        jsonRestaurantNoCuisine = ResourcesUtils.getContentFromResource(
                "/json/restaurant/incorrect/oneRestaurantNoCuisine.json");

        jsonRestaurantCuisineInexistent= ResourcesUtils.getContentFromResource(
                "/json/restaurant/incorrect/oneRestaurantInexistent.json");

        databaseCleaner.clearTables();
        prepareDataForTesting();
    }

    // Valida o Codigo da Resposta 200 ao buscar todas restaurants
    @Test
    public void must_ReturnStatus200_whenQueryAllRestaurants(){

        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    // Valida o Codigo da Resposta 201 ao criar uma cozinha
    @Test
    public void must_ReturnStatus201_whenCreatedRestaurant(){

        given()
            .body(jsonRestaurantCorrect)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    // Valida o Corpo da Resposta
    @Test
    public void must_ReturnTotalRestaurants_whenQueryAllRestaurants(){

        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(totalRegisteredRestaurants));


    }

    // Valida resposta BAD_REQUEST quando o Restaurant sem Delivery Fee
    // Valida resposta BAD_REQUEST quando o Restaurant sem cozinha
    // Valida resposta OK (200) quando consultamos um Restaurant valido
    // Valida resposta BAD_REQUEST quando o Restaurant com cozinha invalida
    @Test
    public void must_ReturnStatus400_WhenRegistringCuisineInexistent() {
        given()
                .body(jsonRestaurantCuisineInexistent)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(BUSINESS_RULE_VIOLATED));
    }

    @Test
    public void must_ReturnStatus400_WhenRegistringRestaurantNoDeliveryFee() {
        given()
                .body(jsonRestaurantNoDeliveryFee)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
         .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }

    @Test
    public void must_ReturnStatus400_WhenRegistringRestaurantNoDCuisine() {
        given()
                .body(jsonRestaurantNoCuisine)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
                .post()
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", equalTo(INVALID_DATA));
    }

    @Test
    public void must_ReturnStatus200AndCorrect_WhenQueryValidRestaurant() {

        given()
                .pathParam("restaurantId", barbacueRestaurant.getId())
                .accept(ContentType.JSON)
        .when()
                .get("/{restaurantId}")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(barbacueRestaurant.getName()));
    }

    // Valida resposta BAD_REQUEST quando o Restaurant com cozinha invalida
    @Test
    public void must_ReturnStatus400_WhenRegistringRestaurantInexistent() {
        given()
                .pathParam("restaurantId", RESTAURANT_ID_INEXISTENT)
                .contentType(ContentType.JSON)
        .when()
                .get("/{restaurantId}")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("title", equalTo(RESOURCE_NO_FOUND));
    }


    private void prepareDataForTesting() {
        Cuisine portuguesCuisine = new Cuisine();
        portuguesCuisine.setName("Portuguese");
        cuisineRepository.save(portuguesCuisine);

        Cuisine frenchCuisine = new Cuisine();
        frenchCuisine.setName("French");
        cuisineRepository.save(frenchCuisine);

        barbacueRestaurant = new Restaurant();
        barbacueRestaurant.setName("Churrascaria Gaucha");
        barbacueRestaurant.setDeliveryFee(new BigDecimal(10));
        barbacueRestaurant.setCuisine(portuguesCuisine);
        restaurantRepository.save(barbacueRestaurant);

        Restaurant frenchRestaurant = new Restaurant();
        frenchRestaurant.setName("Pyramids Restaurant");
        frenchRestaurant.setDeliveryFee(new BigDecimal(18));
        frenchRestaurant.setCuisine(frenchCuisine);
        restaurantRepository.save(frenchRestaurant);

        totalRegisteredRestaurants = (int) restaurantRepository.count();
    }
}
