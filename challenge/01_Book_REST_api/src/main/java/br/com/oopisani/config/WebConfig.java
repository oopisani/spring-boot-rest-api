package br.com.oopisani.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Sobreescreve método em vez criarmos algo, então não precisa de Bean
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        // Via EXTENSION. http://localhost:8080/api/person/v1/2.xml or http://localhost:8080/api/person/v1/2.JSON Deprecated on Spring Boot 2.6

        // VIA QUERY PARAM http://localhost:8080/api/person/v1/2?mediaType=xml
//        configurer.favorParameter(true)
//                .parameterName("mediaType")
//                .ignoreAcceptHeader(true)
//                .useRegisteredExtensionsOnly(false)
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("xml", MediaType.APPLICATION_XML);

        // VIA HEADER PARAM http://localhost:8080/api/person/v1/2

        // não usar parâmetro da URL para decidir o formato (?mediaType=json, por exemplo)
        configurer.favorParameter(false)
//                .parameterName("mediaType")
                // considerar o header Accept. Ele considera o que o cliente pediu no Accept antes de aplicar o nosso defaultContentType.
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                // se nenhuma preferência for informada, usar JSON
                .defaultContentType(MediaType.APPLICATION_JSON)
                // registra json como application/json
                .mediaType("json", MediaType.APPLICATION_JSON)
                // registra xml como application/xml
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("yaml", MediaType.APPLICATION_YAML);




    }
}
