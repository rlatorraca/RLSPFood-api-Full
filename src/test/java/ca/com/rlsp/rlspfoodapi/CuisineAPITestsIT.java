package ca.com.rlsp.rlspfoodapi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import ca.com.rlsp.rlspfoodapi.domain.model.Cuisine;
import ca.com.rlsp.rlspfoodapi.domain.repository.CuisineRepository;
import ca.com.rlsp.rlspfoodapi.util.DatabaseCleaner;
import ca.com.rlsp.rlspfoodapi.util.ResourcesUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //Levanta um Servidor Web para testar
@TestPropertySource("/application_test.properties") // Para fazer as mudancas necessarias para o DB de tests
public class CuisineAPITestsIT {

    private static final int CUISINE_ID_INEXISTENT = 1000000;
    private Cuisine canadianCuisine;
    private int totalRegisteredCuisines;
    private String jsonOneCuisine;

    @LocalServerPort // Como estamos usando RANDOM_PORT (porta aleatoria) temos que pegar essa porta para entao fazermos a conexao com o servidor WEB mock
    private int randomPort;


    @Autowired // Adiciona uma instancia do Flyway para podermos usar a mesma massa de dados para cada teste
    private Flyway flyway;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    /*
        METODO DE CALLBACK
         - Executado antes dos testes de API
     */
    @BeforeEach
    public void setUp(){
       RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // Quando falhar mostrara: REQUISICAO e RESPOSTA
       RestAssured.port = randomPort;
       RestAssured.basePath = "/cuisines";

       //flyway.migrate();
       // Limpa e prapra os dados a cada teste
       databaseCleaner.clearTables();
       prepareDataForTesting();

       jsonOneCuisine = ResourcesUtils.getContentFromResource("/json/cuisine/correct/oneCuisine.json");
    }

    // Valida o Codigo da Resposta 200 ao buscar todas cozinhas
    @Test
    public void must_ReturnStatus200_whenQueryAllCuisines(){

        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }
    // Valida o Codigo da Resposta 201 ao criar uma cozinha

    @Test
    public void must_ReturnStatus201_whenCreatedCuisine(){

        given()
            .body(jsonOneCuisine)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
        .when()
            .post()
        .then()
             .statusCode(HttpStatus.CREATED.value());
    }
    // Valida o Corpo da Resposta

    @Test
    public void must_ReturnTotalCuisines_whenQueryAllCuisines(){

        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .body("", Matchers.hasSize(totalRegisteredCuisines))
            .body("nome", Matchers.hasItems("American", "Chinese"));

    }
    // Testa passando parametros (Retornando STATUS CODE 200 e nome da cozinha
    // GET /cuisine/{id}

    @Test
    public void must_ReturnStatusAndOneCuisine_whenSentIdAndExistent(){
        given()
            .pathParam("cuisineId", canadianCuisine.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cuisineId}")
        .then()
             .statusCode(HttpStatus.OK.value())
             .body("nome", equalTo(canadianCuisine.getName()));
    }
    // Testa passando parametros (Retornando STATUS CODE and
    // GET /cuisine/{id}

    @Test
    public void must_ReturnStatus404_whenQueryInexistentCuisine(){

        given()
            .pathParam("cuisineId", CUISINE_ID_INEXISTENT)
            .accept(ContentType.JSON)
        .when()
            .get("/{cuisineId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body("nome", equalTo(null));
    }

    private void prepareDataForTesting() {

        canadianCuisine = new Cuisine();
        canadianCuisine.setName("Canadian");
        cuisineRepository.save(canadianCuisine);

        Cuisine cuisine02 = new Cuisine();
        cuisine02.setName("American");
        cuisineRepository.save(cuisine02);

        Cuisine cuisine03 = new Cuisine();
        cuisine03.setName("Chinese");
        cuisineRepository.save(cuisine03);
        totalRegisteredCuisines = (int) cuisineRepository.count();
    }


}
