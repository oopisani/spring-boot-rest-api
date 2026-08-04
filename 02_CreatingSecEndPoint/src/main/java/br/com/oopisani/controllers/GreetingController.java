package br.com.oopisani.controllers;

import br.com.oopisani.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // http://localhost:8080/greeting?name=Leandro
   @RequestMapping("/greeting")
    public Greeting greeting(
            // pro http ler o parametro do metodo greeting..
            @RequestParam(value = "name", defaultValue = "World")
            String name) {;
       return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
