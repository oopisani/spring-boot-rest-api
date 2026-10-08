package br.com.oopisani.integrationtests.testcontainers;


import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;



@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)

public class AbstractIntegrationTest {

    // Class Intializer
    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        // Container MySQL que será utilizado durante os testes de integração.
        static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:9.1.0");

        // Inicializa o container MySQL.
        private static void startContainers() {
            Startables.deepStart(Stream.of(mysql)).join();
        }


        // Cria as configurações de conexão com o MySQL.
        // Os valores são obtidos dinamicamente nesse método, através do Testcontainer quando ele é iniciado.
        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", mysql.getJdbcUrl(),
                    "spring.datasource.username", mysql.getUsername(),
                    "spring.datasource.password", mysql.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            // O Environment reúne as fontes de configurações que o Spring pode consultar.
            // É como uma "grande coleção" de configurações.

            // PropertySource é uma fonte de propriedades, e o Environment pode ter várias,
            // formando uma lista de fontes.

            // Inicializa o MySQL para podermos pegar suas informações de conexão.
            startContainers();

            // Pega o Environment do contexto da nossa aplicação/teste.
            ConfigurableEnvironment environment = applicationContext.getEnvironment();

            // Cria uma "fonte" de propriedades usando o Map
            // retornado por createConnectionConfiguration().
            MapPropertySource testcontainers = new MapPropertySource(
                    "testcontainers",
                    (Map) createConnectionConfiguration());

            // Coloca essa fonte de propriedades dentro do Environment como prioridade.
            // addFirst() coloca testcontainers no primeiro lugar da lista de PropertySources.
            environment.getPropertySources().addFirst(testcontainers);
        }


    }
}