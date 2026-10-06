package br.com.oopisani.repository;

import br.com.oopisani.model.Book;
import br.com.oopisani.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}