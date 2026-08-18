package br.com.oopisani.repository;

import br.com.oopisani.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

// Extends recebe um tipo da classe que eu escolher E também o tipo do ID, que no nosso caso em Person é representado por Long.
public interface PersonRepository extends JpaRepository<Person, Long> {
}
