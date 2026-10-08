package br.com.oopisani.config;

public interface TestConfigs {
    int SERVER_PORT = 8888;

    String HEADER_PARAM_AUTHORIZATION = "Authorization";
    String HEADER_PARAM_ORIGIN = "Origin";

    String ORIGIN_GITHUB = "https://github.com/oopisani/spring-boot-rest-api";
    String ORIGIN_GITHUBWRONG= "https://github.com/oopisani";
    String ORIGIN_LOCALHOST= "http://localhost:8080";
}
