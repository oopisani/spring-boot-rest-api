package br.com.oopisani.services;

import br.com.oopisani.data.dto.BookDTO;
import br.com.oopisani.exception.RequiredObjectIsNullException;
import br.com.oopisani.model.Book;
import br.com.oopisani.repository.BookRepository;
import br.com.oopisani.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Cria a instância da classe para teste e reutiliza
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    MockBook input;

    // @InjectMocks -> Cria instância da classe e injeta @Mock nela
    @InjectMocks
    private BookServices service;
    // @Mock -> Cria mock
    @Mock
    // Para implementarmos JPARepository (métodos oficiais do JPA)
    BookRepository repository;

    // @BeforeEach -> Vai ser executado antes dos métodos com @Test
    @BeforeEach
    void setUp() {
        // Inicializa input
        input = new MockBook();
        // Método static openMocks que l
        //  ê os atributos dessa classe (this) +
        // Procura as anottations para construir os objetos falsos (mocks) e fazer as injeções
        MockitoAnnotations.openMocks(this);
    }

    // DINÂMICA MOCKITO:::
    @Test
    void findById() {
        // Declara obj do tipo Book com uma entidade book mockada
        Book book = input.mockEntity(1);
        book.setId(1L);
        // REGRA mockito. Não retorna resultado, apenas define regras nos testes.
        //"Quando o método findById tiver parâmetro=1 então retorna esse book criado aqui"
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        // Ele vai tentar ir no banco (dentro do método findById em Service) buscar a pessoa (repository.findById).
        // O Mockito percebe isso, lembra do treinamento (o when) e joga aquele obj Book na mão do service.
        // Retornamos um PersonDTO (convertido de entidade para DTO pelo PersonServices)
        BookDTO result = service.findById(1L);

        // Garantir que o meu service não devolveu um objeto vazio e que o ID não se perdeu no caminho:
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "self".equals(link.getRel().value())
                    && link.getHref().endsWith("/api/book/v1/1")
                && "GET".equals(link.getType())

                ));

        assertTrue(result.getLinks().stream()
                        .anyMatch(link -> "findAll".equals(link.getRel().value())
                                && link.getHref().endsWith("/api/book/v1")
                                && "GET".equals(link.getType())

                        )

        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "create".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "POST".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "update".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "PUT".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "delete".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1/1")
                        && "DELETE".equals(link.getType())
                )
        );
        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, result.getPrice());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(result.getLaunchDate());

    }

    @Test
    void create() {

        // Cria entidade do tipo Person e passa de resultado método mockEntity que aceita number
        Book book = input.mockEntity(1);
        Book persisted = book;

        // Garante que number será long
        persisted.setId(1L);
        // Cria obj DTO  e passa de resultado método mockDTO que aceita number
        BookDTO dto = input.mockDTO(1);

        // REGRA MOCKITO: "Quando o service chamar repository.save()
        // criando a entidade que ele acabou de converter a partir do DTO, intercepta save() e faz ele RETORNAR o objeto 'persisted' (que já tem ID).
        // when(repository.save(book)).thenReturn(persisted);
        // Usamos any() porque o Service instancia uma nova entidade (com novo endereço de memória e data):
        when(repository.save(any(Book.class))).thenReturn(persisted);

        // De acordo com as regras de create, passamos um dto. E lá dentro terá conversões.
        var result = service.create(dto);


        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "self".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1/1")
                        && "GET".equals(link.getType())

                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "findAll".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "GET".equals(link.getType())

                )

        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "create".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "POST".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "update".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "PUT".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "delete".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1/1")
                        && "DELETE".equals(link.getType())
                )
        );
        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, result.getPrice());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(result.getLaunchDate());
    }

        @Test
        // assertThrows = um método usado em testes unitários para verificar se um bloco de código lança o tipo correto de exceção.
        // 1o argumento = o tipo de exceção que você espera que acontença
        // 2o argumento = um bloco de código que deve gerar essa falha
        void testCreateWithNullPerson() {
            Exception exception = assertThrows(RequiredObjectIsNullException.class,
                    () -> {
                        service.create(null);
                    });

            String expectedMessage = "It is not allowed to persist a null object!";
            String actualMessage = exception.getMessage();
             // Função de asserção, veirfica uma condição ou expressão booleana
            // Se o valor passado como argumento for true, o teste passa sem problemas. se false, não passa.
            assertTrue(actualMessage.contains(expectedMessage));

        }


    @Test
    void update() {

        // Cria o obj simulando como ele está no banco
        Book book = input.mockEntity(1);
        // Cria um objeto simulando como ele deve sair APÓS ser salvo
        Book persisted = book;
        persisted.setId(1L);

        // Cria O DTO com os dados novos que vieram da requisição
        BookDTO dto = input.mockDTO(1);

        // Simula a ida ao banco. Quando o Service buscar pelo ID 1, o Mockito entrega o objeto 'book'.
        when(repository.findById(1L)).thenReturn(Optional.of(book));
        // O Service vai alterar este mesmo objeto 'book' na memória (usando os setters).
        // Quando ele tentar salvar essa entidade, o Mockito intercepta a ação e retorna o objeto 'persisted'."
        // P.S: Induzindo um comportamento, forçando o mock a devolver 'persisted' ao receber o 'book'.
        when(repository.save(book)).thenReturn(persisted);
        //  Executa a chamada do método. O resultado final transformado para DTO será guardado em 'result'.
        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "self".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1/1")
                        && "GET".equals(link.getType())

                ));

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "findAll".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "GET".equals(link.getType())

                )

        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "create".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "POST".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "update".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1")
                        && "PUT".equals(link.getType())
                )
        );

        assertTrue(result.getLinks().stream()
                .anyMatch(link -> "delete".equals(link.getRel().value())
                        && link.getHref().endsWith("/api/book/v1/1")
                        && "DELETE".equals(link.getType())
                )
        );
        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, result.getPrice());
        assertEquals("Author Test1", result.getAuthor());
        assertEquals("Title Test1", result.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(result.getLaunchDate());
    }
        @Test
            // assertThrows = um método usado em testes unitários para verificar se um bloco de código lança o tipo correto de exceção.
            // 1o argumento = o tipo de exceção que você espera que acontença
            // 2o argumento = um bloco de código que deve gerar essa falha
        void testUpdateWithNullPerson() {
            Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {service.update(null);
            });
            String expectedMessage = "It is not allowed to persist a null object!";
            String actualMessage = exception.getMessage();

            // Função de asserção, veirfica uma condição ou expressão booleana
            // Se o valor passado como argumento for true, o teste passa sem problemas. se false, não passa.
            assertTrue(actualMessage.contains(expectedMessage));
        }



        @Test
    void delete() {
        Book book = input.mockEntity(1);
            book.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Book.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
            List<Book> list = input.mockEntityList();
            // Regra do Mock
            when(repository.findAll()).thenReturn(list);
            List<BookDTO> book = service.findAll();

            assertNotNull(book);
            assertEquals(14, book.size());

            var bookOne = book.get(1);

            assertNotNull(bookOne);
            assertNotNull(bookOne.getId());
            assertNotNull(bookOne.getLinks());

        assertTrue(bookOne.getLinks().stream()
                    .anyMatch(link -> "self".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/1")
                            && "GET".equals(link.getType())
                    ));

        assertTrue(bookOne.getLinks().stream()
                    .anyMatch(link -> "findAll".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "GET".equals(link.getType())
                    )
            );

        assertTrue(bookOne.getLinks().stream()
                    .anyMatch(link -> "create".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "POST".equals(link.getType())
                    )
            );

        assertTrue(bookOne.getLinks().stream()
                    .anyMatch(link -> "update".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "PUT".equals(link.getType())
                    )
            );

        assertTrue(bookOne.getLinks().stream()
                    .anyMatch(link -> "delete".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/1")
                            && "DELETE".equals(link.getType())
                    )
            );

        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, bookOne.getPrice());
        assertEquals("Author Test1", bookOne.getAuthor());
        assertEquals("Title Test1", bookOne.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(bookOne.getLaunchDate());

        var bookFour = book.get(4);

            assertNotNull(bookFour);
            assertNotNull(bookFour.getId());
            assertNotNull(bookFour.getLinks());

        assertTrue(bookFour.getLinks().stream()
                    .anyMatch(link -> "self".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/4")
                            && "GET".equals(link.getType())
                    ));

        assertTrue(bookFour.getLinks().stream()
                    .anyMatch(link -> "findAll".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "GET".equals(link.getType())
                    )
            );

        assertTrue(bookFour.getLinks().stream()
                    .anyMatch(link -> "create".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "POST".equals(link.getType())
                    )
            );

        assertTrue(bookFour.getLinks().stream()
                    .anyMatch(link -> "update".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "PUT".equals(link.getType())
                    )
            );

        assertTrue(bookFour.getLinks().stream()
                    .anyMatch(link -> "delete".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/4")
                            && "DELETE".equals(link.getType())
                    )
            );

        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, bookFour.getPrice());
        assertEquals("Author Test4", bookFour.getAuthor());
        assertEquals("Title Test4", bookFour.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(bookFour.getLaunchDate());

        var bookSeven = book.get(7);

            assertNotNull(bookSeven);
            assertNotNull(bookSeven.getId());
            assertNotNull(bookSeven.getLinks());

        assertTrue(bookSeven.getLinks().stream()
                    .anyMatch(link -> "self".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/7")
                            && "GET".equals(link.getType())
                    ));

        assertTrue(bookSeven.getLinks().stream()
                    .anyMatch(link -> "findAll".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "GET".equals(link.getType())
                    )
            );

        assertTrue(bookSeven.getLinks().stream()
                    .anyMatch(link -> "create".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "POST".equals(link.getType())
                    )
            );

        assertTrue(bookSeven.getLinks().stream()
                    .anyMatch(link -> "update".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1")
                            && "PUT".equals(link.getType())
                    )
            );

        assertTrue(bookSeven.getLinks().stream()
                    .anyMatch(link -> "delete".equals(link.getRel().value())
                            && link.getHref().endsWith("/api/book/v1/7")
                            && "DELETE".equals(link.getType())
                    )
            );

        // Após testes OK, conferir por fim se o resultado é igual ao esperado:
        assertEquals(25D, bookSeven.getPrice());
        assertEquals("Author Test7", bookSeven.getAuthor());
        assertEquals("Title Test7", bookSeven.getTitle());
        // Como a data vem nova a cada rodada, apenas conferimos que não é nula.
        assertNotNull(bookSeven.getLaunchDate());
        }
}