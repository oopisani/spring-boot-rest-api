package br.com.oopisani.unittests.mapper.mocks;

import br.com.oopisani.data.dto.BookDTO;
import br.com.oopisani.model.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {


    // ==========================================
    // 1. MÉTODOS DE ATALHO (SEM PARÂMETRO)
    // ==========================================


    // Atalho para criar uma entidade Book genérica.
    // Usado quando o teste não se importa com os dados exatos do objeto.

    public Book mockEntity() {
        // Chama a fábrica principal passando 0 como padrão
        return mockEntity(0);
    }

    // Atalho para criar um objeto BookDTO genérico.
    public BookDTO mockDTO() {
        return mockDTO(0);
    }


    // ==========================================
    // 2. MÉTODOS DE LOTE (LISTAS)
    // ==========================================

    // Fabrica uma lista com 14 entidades Book diferentes.
    // Útil para testar métodos que retornam múltiplos resultados (ex: findAll).


    // Retorna lista de entidade
    public List<Book> mockEntityList() {
        List<Book> persons = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    // Retorna lista de obj DTO
    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }



    // ==========================================
    // 3. FÁBRICAS REAIS (COM PARÂMETRO)
    // ==========================================

    // A fábrica real que constrói a entidade Book.
    // Recebe um número para gerar dados únicos e evitar repetição.


    // cria entidade
    public Book mockEntity(Integer number) {
        // instância
        Book book = new Book();
        // seta atributos
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setId(number.longValue());
        // retorna entidade
        return book;
    }

    // cria obj DTO
    public BookDTO mockDTO(Integer number) {
        // instância
        BookDTO book = new BookDTO();
        // seta atributos
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(25D);
        book.setId(number.longValue());
        // retorna DTO
        return book;
    }
}
