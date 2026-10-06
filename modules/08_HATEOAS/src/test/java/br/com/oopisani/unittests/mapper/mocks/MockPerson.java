package br.com.oopisani.unitetests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.oopisani.data.dto.PersonDTO;
import br.com.oopisani.model.Person;

public class MockPerson {

    // ==========================================
    // 1. MÉTODOS DE ATALHO (SEM PARÂMETRO)
    // ==========================================



     // Atalho para criar uma entidade Person genérica.
     // Usado quando o teste não se importa com os dados exatos do objeto.

    public Person mockEntity() {
        // Chama a fábrica principal passando 0 como padrão
        return mockEntity(0);
    }

     // Atalho para criar um objeto PersonDTO genérico.

    public PersonDTO mockDTO() {
        return mockDTO(0);
    }

    // ==========================================
    // 2. MÉTODOS DE LOTE (LISTAS)
    // ==========================================


     // Fabrica uma lista com 14 entidades Person diferentes.
     // Útil para testar métodos que retornam múltiplos resultados (ex: findAll).


    // Retorna lista de entidade
    public List<Person> mockEntityList() {
        List<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    // Retorna lista de obj DTO
    public List<PersonDTO> mockDTOList() {
        List<PersonDTO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }

    // ==========================================
    // 3. FÁBRICAS REAIS (COM PARÂMETRO)
    // ==========================================

     // A fábrica real que constrói a entidade Person.
     // Recebe um número para gerar dados únicos e evitar repetição.


    // cria entidade
    public Person mockEntity(Integer number) {
        // instância
        Person person = new Person();
        // seta atributos
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        // retorna entidade
        return person;
    }

    // cria obj MOCK
    public PersonDTO mockDTO(Integer number) {
        // instância
        PersonDTO person = new PersonDTO();
        // seta atributos
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        // retorna obj dto
        return person;
    }

}