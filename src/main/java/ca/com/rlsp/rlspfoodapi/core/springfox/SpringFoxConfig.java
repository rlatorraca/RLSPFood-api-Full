package ca.com.rlsp.rlspfoodapi.core.springfox;

import ca.com.rlsp.rlspfoodapi.api.exceptionhandler.ApiHandleProblemDetail;
import ca.com.rlsp.rlspfoodapi.api.v1.model.dto.output.*;
import ca.com.rlsp.rlspfoodapi.api.v1.openapi.model.*;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CityOutputDtoV2;
import ca.com.rlsp.rlspfoodapi.api.v2.model.output.CuisineOutputDtoV2;
import com.fasterxml.classmate.TypeResolver;
import org.hibernate.validator.constraints.URL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/*
    End Pooint documentation
 */
@Configuration
@EnableOpenApi
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class) // Faz a conexão do BeanValidator do SpringBoot com o SpringFox(OpenApi)
public class SpringFoxConfig implements WebMvcConfigurer {


    private static final String MSG_INTERNAL_SERVER_ERROR = "Internal Server Error" ;
    private static final String MSG_NOT_ACCEPTABLE = "Resource may not be acceptable by Consumer";
    private static final String MSG_BAD_REQUEST = "Invalid Request (client error)";
    private static final String MSG_UNSUPPORTED_MEDIA_TYPE = "Request denied. Unsupported format";

    TypeResolver typeResolver = new TypeResolver();

    /*
        Docket => classe do SpringFox que representa a configuracao da API para gerar a documentacao com a especificacao
            OpenAPI
    */
    @Bean
    public Docket apiDockerV1() {
        // instancia o conjunto de servicos que deve ser documentado
        return new
                Docket(DocumentationType.OAS_30)
                    .select() // seleciona os endpoints que serao expostos
                        //.apis(RequestHandlerSelectors.any()) // tudo relacionado a API sera incluido (inlcusive do SpringBoot
                        .apis(RequestHandlerSelectors.basePackage("ca.com.rlsp.rlspfoodapi.api"))
                        //.paths(PathSelectors.any())
                        .paths(PathSelectors.ant("/v1/**"))
                        //.paths(PathSelectors.ant("/restaurants/*")) // apenas o que tiver dentro de restaurnt vai ser mostrado
                        .build()
                .apiInfo(rlspApiInfoV1())
                .groupName("V1")
                .useDefaultResponseMessages(false) // Desabilita os codigo de resposta Standard para ERRORs
                .globalResponses(HttpMethod.GET, globalMsgErrorResponseMessagesToGET()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.POST, globalMsgErrorResponseMessagesToPOST()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.PUT, globalMsgErrorResponseMessagesToPUT()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.DELETE, globalMsgErrorResponseMessagesToDELETE()) // Customized Msgs de ERROR para o GET
                .additionalModels(typeResolver.resolve(ApiHandleProblemDetail.class)) // Usado para modificar nomes de retorno, atributos, exemplos, etc na documentacao da OpenApi
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // Faz a troca na documentacao de Pageable por PageableModelOpenApi
                .directModelSubstitute(Link.class, LinksModelOpenApi.class) // Faz a troca na documentacao de Links (errados) para LinkMOdelOpenAPi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CuisineOutputDto.class),
                        CuisineModelOpenApi.class)) // Resolve um Page<CuisineOutputDto> para um CuisineControllerOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProvinceOutputDto.class),
                        ProvincesModelApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um CuisineControllerOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CityOutputDto.class),
                        CitiesModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um CitiesModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, GroupOutputDto.class),
                        GroupsModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um GroupsModelApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, PermissionOutputDto.class),
                        GroupsModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um CitiesModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, OrderOutputDto.class),
                        OrdersModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um CitiesModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProductOutputDto.class),
                        ProductsModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um ProductsModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, ProductOutputDto.class),
                        ProductsModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um ProductsModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, RestaurantOutputDto.class),
                        RestaurantsModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um RestaurantsBasicsModelOpenApi

                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, UserOutputDto.class),
                        UsersModelOpenApi.class)) // Resolve um CollectionModel<CuisineOutputDto> para um RestaurantsBasicsModelOpenApi

                //.genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(ResponseEntity.class,
                                typeResolver.resolve(CollectionModel.class ,PaymentTypeOutputDto.class)),
                        typeResolver.resolve(PaymentsTypesModelOpenApi.class))) // Resolve um ResponseEntity<CollectionModel<CuisineOutputDto> para um PaymentsTypesModelOpenApi
                                                          // Precisa de mudancas no PaymentTypeConrollerOpenApi
                //.alternateTypeRules(buildAlternateTypeRule(OrderOutputDto.class)) // Resolve um Page<OrderOutput> para um OrderControllerOpenApi
                .alternateTypeRules(
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(CollectionModel.class, CuisineOutputDto.class),
                                typeResolver.resolve(CuisineOutputDto.class)))
                .ignoredParameterTypes(ignoredParameterTypesClasses()) // Ignora qualquer parametro do tipo ServletWebRequest (usado no PaymentTyoeController)
//                .globalRequestParameters(Collections.singletonList(
//                        new RequestParameterBuilder()
//                                .name("fields")
//                                .description("Properties names used to filter query, split by comma")
//                                .in(ParameterType.QUERY)
//                                .required(false)
//                                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
//                                .build())
//                )
                .tags(
                        new Tag("Cities", "Manage all endpoints to City's Resources"),
                        new Tag("Cuisines", "Manage all endpoints to Cuisine's Resources"),
                        new Tag("Groups", "Manage all endpoints to Group's Resources"),
                        new Tag("Orders", "Manage all endpoints to Order's Resource"),
                        new Tag("Payment Types", "Manage all endpoints to Payment Types' Resource"),
                        new Tag("Provinces", "Manage all endpoints to Province's Resources"),
                        new Tag("Restaurants", "Manage all endpoints to Restaurant's Resources"),
                        new Tag("Restaurant Products", "Manage all endpoints to Restaurant-Product's Resources"),
                        new Tag("Restaurant Products Photos", "Manage all endpoints to Restaurant-Products-Photo's Resources"),
                        new Tag("Statistics", "Manage all endpoints to Statistic's Resources"),
                        new Tag("Permission", "Manage all endpoints to Permission's Resources"),
                        new Tag("Users", "Manage all endpoints to User's Resources")

                );
    }

    @Bean
    public Docket apiDockerV2() {
        // instancia o conjunto de servicos que deve ser documentado
        return new
                Docket(DocumentationType.OAS_30)

                .select() // seleciona os endpoints que serao expostos
                    .apis(RequestHandlerSelectors.basePackage("ca.com.rlsp.rlspfoodapi.api"))
                    .paths(PathSelectors.ant("/v2/**"))
                .build()
                .apiInfo(rlspApiInfoV2())
                .groupName("V2")
                .useDefaultResponseMessages(false) // Desabilita os codigo de resposta Standard para ERRORs
                .globalResponses(HttpMethod.GET, globalMsgErrorResponseMessagesToGET()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.POST, globalMsgErrorResponseMessagesToPOST()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.PUT, globalMsgErrorResponseMessagesToPUT()) // Customized Msgs de ERROR para o GET
                .globalResponses(HttpMethod.DELETE, globalMsgErrorResponseMessagesToDELETE()) // Customized Msgs de ERROR para o GET
                .additionalModels(typeResolver.resolve(ApiHandleProblemDetail.class)) // Usado para modificar nomes de retorno, atributos, exemplos, etc na documentacao da OpenApi
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class) // Faz a troca na documentacao de Pageable por PageableModelOpenApi
                .directModelSubstitute(Link.class, LinksModelOpenApi.class) // Faz a troca na documentacao de Links (errados) para LinkMOdelOpenAPi
                .ignoredParameterTypes(ignoredParameterTypesClasses())
                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(PagedModel.class, CuisineOutputDtoV2.class),
                        CuisineOutputDtoV2.class)) // Resolve um Page<CuisineOutputDto> para um CuisineControllerOpenApi


                .alternateTypeRules(AlternateTypeRules.newRule(
                        typeResolver.resolve(CollectionModel.class, CityOutputDtoV2.class),
                        CityOutputDtoV2.class)) // Resolve um CollectionModel<CuisineOutputDto> para um CitiesModelOpenApi

                .tags(
                        new Tag("Cities", "Manage all endpoints to City's Resources"),
                        new Tag("Cuisines", "Manage all endpoints to Cuisine's Resources")

                );
    }

    /*
    private <T> AlternateTypeRule buildAlternateTypeRule(Class<T> classModel) {
        return AlternateTypeRules.newRule(
                typeResolver.resolve(Page.class, classModel),
                typeResolver.resolve(PageModelOpenApi.class, classModel));

    }
    */



    /*
        Mostra os caminhos (path) para servir arquivos estaticos (html, css, js) do SpringFox API (Ex: HTML page)
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("index.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/META-INF/resources/fonts/");
    }

    private Class<?>[] ignoredParameterTypesClasses() {
        return Arrays.asList(
                ServletWebRequest.class,
                URL.class,
                URI.class,
                URLStreamHandler.class,
                Resource.class,
                File.class,
                InputStream.class,
                Pageable.class,
                Page.class,
                Sort.class
        ).toArray(new Class[0]);
    }

    private ApiInfo rlspApiInfoV1(){
        return new ApiInfoBuilder()
                .title("RLSP FOOD API (Deprecated)")
                .description("API for Canada and Maritimes restaurants.<br/>This API is deprecated and deadline to use that is December 31, 2021")
                .version("1.34")
                .contact(new Contact("RLSPFood", "https://www.rlspfood.api.com.ca", "contact@rlspfood.api.com.ca"))
                .build();

    }
    private ApiInfo rlspApiInfoV2(){
        return new ApiInfoBuilder()
                .title("RLSP FOOD API")
                .description("API for Canada and Maritimes restaurants")
                .version("2.34")
                .contact(new Contact("RLSPFood", "https://www.rlspfood.api.com.ca", "contact@rlspfood.api.com.ca"))
                .build();

    }

    private List<Response> globalMsgErrorResponseMessagesToGET() {
        return Arrays.asList(
            new ResponseBuilder()
                    .code(convertToString(HttpStatus.INTERNAL_SERVER_ERROR))
                    .description(MSG_INTERNAL_SERVER_ERROR)
                    .representation(MediaType.APPLICATION_JSON)
                    .apply(ApiHandleProblemDetailBuilder())
                    .build(),
            new ResponseBuilder()
                    .code(convertToString(HttpStatus.INTERNAL_SERVER_ERROR))
                    .description(MSG_INTERNAL_SERVER_ERROR)
                    .representation(MediaType.APPLICATION_XML)
                    .apply(ApiHandleProblemDetailBuilder())
                    .build(),
            new ResponseBuilder()
                    .code(convertToString(HttpStatus.NOT_ACCEPTABLE))
                    .description(MSG_NOT_ACCEPTABLE)
                    //.representation(MediaType.APPLICATION_JSON)
                    //.apply(ApiHandleProblemDetailBuilder())
                    .build()
        );
    }

    private List<Response> globalMsgErrorResponseMessagesToPOST() {
        return getGenericMsgErrorResponseMessages();
    }

    private List<Response> globalMsgErrorResponseMessagesToPUT() {
        return getGenericMsgErrorResponseMessages();
    }

    private List<Response> getGenericMsgErrorResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.INTERNAL_SERVER_ERROR))
                        .description(MSG_INTERNAL_SERVER_ERROR)
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(ApiHandleProblemDetailBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.NOT_ACCEPTABLE))
                        .description(MSG_NOT_ACCEPTABLE)
                        //.representation(MediaType.APPLICATION_JSON)
                        //.apply(ApiHandleProblemDetailBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.BAD_REQUEST))
                        .description(MSG_BAD_REQUEST)
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(ApiHandleProblemDetailBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.UNSUPPORTED_MEDIA_TYPE))
                        .description(MSG_UNSUPPORTED_MEDIA_TYPE)
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(ApiHandleProblemDetailBuilder())
                        .build()
        );
    }

    private List<Response> globalMsgErrorResponseMessagesToDELETE() {
        return Arrays.asList(
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.INTERNAL_SERVER_ERROR))
                        .description(MSG_INTERNAL_SERVER_ERROR)
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(ApiHandleProblemDetailBuilder())
                        .build(),
                new ResponseBuilder()
                        .code(convertToString(HttpStatus.BAD_REQUEST))
                        .description(MSG_BAD_REQUEST)
                        .representation(MediaType.APPLICATION_JSON)
                        .apply(ApiHandleProblemDetailBuilder())
                        .build()
        );
    }

    private Consumer<RepresentationBuilder> ApiHandleProblemDetailBuilder() {
        return r -> r.model(
                   m -> m.name("ErrorDetails").referenceModel(
                        ref -> ref.key(
                                k -> k.qualifiedModelName(
                                        q -> q.name("ErrorDetails")
                                                .namespace("ca.com.rlsp.rlspfoodapi.api.exceptionhandler")
                                ))));
    }

    private String convertToString(HttpStatus httpStatus) {
        return String.valueOf(httpStatus.value());
    }
}
