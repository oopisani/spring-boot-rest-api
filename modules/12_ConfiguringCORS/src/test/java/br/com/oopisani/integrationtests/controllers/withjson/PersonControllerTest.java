package br.com.oopisani.integrationtests.controllers.withjson;

import br.com.oopisani.config.TestConfigs;
import br.com.oopisani.integrationtests.dto.PersonDTO;
import br.com.oopisani.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.awt.*;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// Ativa a ordenação explícita dos métodos de teste usando a anotação @Order(...).
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest  extends AbstractIntegrationTest  {

    // Configuração para construir e enviar requisições HTTP reais (via REST Assured)
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        // Inicializa o conversor de JSON
        objectMapper = new ObjectMapper();
        // e diz para ele ignorar propriedades desconhecidas que o servidor mande a mais
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }


    @Test
    @Order(1)
    void create() throws JsonProcessingException {
        mockPerson();

        // Constrói as regras da requisição HTTP (cabeçalhos, porta, filtros de log, endpoint base)
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
                        TestConfigs.ORIGIN_GITHUB)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                   .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                   .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        // Executa o POST real na API enviando o objeto 'person' como JSON no corpo da requisição
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200) // Valida que a API respondeu com sucesso (HTTP 200)
                .extract()
                .body()
                .asString();

        // Converte o texto JSON que voltou do servidor de volta para um objeto Java (PersonDTO)
      PersonDTO createdPerson =  objectMapper.readValue(content, PersonDTO.class);

        // Atualiza a variável global com o objeto que voltou do banco (agora contendo o ID gerado)
        // para que os próximos testes (@Order(2), etc.) saibam qual ID consultar/atualizar/deletar.
      person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());


    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {

        // Constrói as regras da requisição HTTP (cabeçalhos, porta, filtros de log, endpoint base)
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN,
                        TestConfigs.ORIGIN_GITHUBWRONG)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        // Executa o POST real na API enviando o objeto 'person' como JSON no corpo da requisição
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(403) // Forbidden
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);


    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GITHUB)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);

        assertEquals("Richard", createdPerson.getFirstName());
        assertEquals("Stallman", createdPerson.getLastName());
        assertEquals("New York City - New York - USA", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
    }
    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_GITHUBWRONG)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }




    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }

    // Método auxiliar (que tem "mock" no nome, mas só cria massa de dados estática)
    // para preencher os campos do DTO antes do envio da requisição
    private void mockPerson() {
        person.setFirstName("Richard");
        person.setLastName("Stallman");
        person.setAddress("New York City - New York - USA");
        person.setGender("Male");
    }
}