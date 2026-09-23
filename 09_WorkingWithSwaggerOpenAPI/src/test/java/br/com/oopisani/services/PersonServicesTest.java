package br.com.oopisani.services;

import br.com.oopisani.data.dto.PersonDTO;
import br.com.oopisani.exception.RequiredObjectIsNullException;
import br.com.oopisani.model.Person;
import br.com.oopisani.repository.PersonRepository;
import br.com.oopisani.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

    // Cria a instância da classe para teste e reutiliza
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    // Cria instância da classe e injeta @Mock
    @InjectMocks
    private PersonServices service;
    // Cria mock
    @Mock
    // Implementa JPARepository  (métodos oficiais do JPA)
    PersonRepository repository;
    // Vai ser executado antes dos métodos com @Test
    @BeforeEach
    void setUp() {
        // Instância obj do tipo MockPerson, já declarado no início
        input = new MockPerson();
        // Lê os atributos dessa classe (this) + procura as anottations para construir os objetos falsos (mocks) e fazer as injeções
        MockitoAnnotations.openMocks(this);
    }

    // DINÂMICA MOCKITO:::
    @Test
    void findById() {
        // Cria entidade do tipo Person e passa de resultado método mockEntity que aceita number
        Person person = input.mockEntity(1);
        person.setId(1L);
        // REGRA mockito. Não retorna resultado, apenas define regras nos testes.
        //"Quando o método findById tiver parâmetro=1 então retorna esse person criado aqui"
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        // Ele vai tentar ir no banco (dentro do método findById em Service) buscar a pessoa (repository.findById).
        // O Mockito percebe isso, lembra do treinamento (o when) e joga aquele boneco Person na mão do service.
        // Retornamos um PersonDTO (convertido de entidade para DTO pelo PersonServices)
        var result = service.findById(1L);

        // Garantir que o meu service não devolveu um objeto vazio e que o ID não se perdeu no caminho:
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                    && link.getHref().endsWith("/api/person/v1/1")
                && link.getType().equals("GET")

                ));

                assertNotNull(result.getLinks().stream()
                        .anyMatch(link -> link.getRel().value().equals("findAll")
                                && link.getHref().endsWith("/api/person/v1")
                                && link.getType().equals("GET")

                        )

        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());

    }

    @Test
    void create() {

        // Cria entidade do tipo Person e passa de resultado método mockEntity que aceita number
        Person person = input.mockEntity(1);
        Person persisted = person;

        // Garante que number será long
        persisted.setId(1L);
        // Cria obj DTO  e passa de resultado método mockDTO que aceita number
        PersonDTO dto = input.mockDTO(1);

        // REGRA MOCKITO: "Quando o service chamar repository.save(),
        //  criando a entidade que ele acabou de converter a partir do DTO, intercepta save() e faz ele RETORNAR o objeto 'persisted' (que já tem ID)."
        when(repository.save(person)).thenReturn(persisted);

        // De acordo com as regras de create, passamos um dto. E lá dentro terá conversões.
        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")

                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")

                )

        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
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

        Person person = input.mockEntity(1);
        Person persisted = person;
        persisted.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        // Procurar para garantir que existe, antes de atualizar.
        // "Quando chamado findById dentro de repository, retorne o obj person criado aqui"
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        // "Quando chamado save dentro de repository, retorna o obj persisted criado aqui"
        when(repository.save(person)).thenReturn(persisted);

        //
        var result = service.update(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("GET")

                ));

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("findAll")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("GET")

                )

        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("create")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("POST")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("update")
                        && link.getHref().endsWith("/api/person/v1")
                        && link.getType().equals("PUT")
                )
        );

        assertNotNull(result.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("delete")
                        && link.getHref().endsWith("/api/person/v1/1")
                        && link.getType().equals("DELETE")
                )
        );
        assertEquals("Address Test1", result.getAddress());
        assertEquals("First Name Test1", result.getFirstName());
        assertEquals("Last Name Test1", result.getLastName());
        assertEquals("Female", result.getGender());
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
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(1L);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findAll() {
            List<Person> list = input.mockEntityList();
            // Regra do Mock
            when(repository.findAll()).thenReturn(list);
            List<PersonDTO> people = service.findAll();

            assertNotNull(people);
            assertEquals(14, people.size());

            var personOne = people.get(1);

            assertNotNull(personOne);
            assertNotNull(personOne.getId());
            assertNotNull(personOne.getLinks());

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/1")
                            && link.getType().equals("GET")
                    ));

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")
                    )
            );

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")
                    )
            );

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")
                    )
            );

            assertNotNull(personOne.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/1")
                            && link.getType().equals("DELETE")
                    )
            );

            assertEquals("Address Test1", personOne.getAddress());
            assertEquals("First Name Test1", personOne.getFirstName());
            assertEquals("Last Name Test1", personOne.getLastName());
            assertEquals("Female", personOne.getGender());

            var personFour = people.get(4);

            assertNotNull(personFour);
            assertNotNull(personFour.getId());
            assertNotNull(personFour.getLinks());

            assertNotNull(personFour.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/4")
                            && link.getType().equals("GET")
                    ));

            assertNotNull(personFour.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")
                    )
            );

            assertNotNull(personFour.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")
                    )
            );

            assertNotNull(personFour.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")
                    )
            );

            assertNotNull(personFour.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/4")
                            && link.getType().equals("DELETE")
                    )
            );

            assertEquals("Address Test4", personFour.getAddress());
            assertEquals("First Name Test4", personFour.getFirstName());
            assertEquals("Last Name Test4", personFour.getLastName());
            assertEquals("Male", personFour.getGender());

            var personSeven = people.get(7);

            assertNotNull(personSeven);
            assertNotNull(personSeven.getId());
            assertNotNull(personSeven.getLinks());

            assertNotNull(personSeven.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("self")
                            && link.getHref().endsWith("/api/person/v1/7")
                            && link.getType().equals("GET")
                    ));

            assertNotNull(personSeven.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("findAll")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("GET")
                    )
            );

            assertNotNull(personSeven.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("create")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("POST")
                    )
            );

            assertNotNull(personSeven.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("update")
                            && link.getHref().endsWith("/api/person/v1")
                            && link.getType().equals("PUT")
                    )
            );

            assertNotNull(personSeven.getLinks().stream()
                    .anyMatch(link -> link.getRel().value().equals("delete")
                            && link.getHref().endsWith("/api/person/v1/7")
                            && link.getType().equals("DELETE")
                    )
            );

            assertEquals("Address Test7", personSeven.getAddress());
            assertEquals("First Name Test7", personSeven.getFirstName());
            assertEquals("Last Name Test7", personSeven.getLastName());
            assertEquals("Female", personSeven.getGender());
        }
}