package br.com.oopisani.controllers.docs;

import br.com.oopisani.data.dto.BookDTO;
import br.com.oopisani.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BookControllerDocs {


    @Operation(

            summary = "Finds a Book",
            description = "Find a specific book by ID",
            // Serve para agrupar endpoints específicos. Nesse caso, endpoints do tipo People.
            tags = {"Book"},
            // Responses serve para documentar todos os cenários de resposta (HTTP Status Code) que o nosso endpoint
            // pode retornar. Funciona como um aviso antecipado para o dev que consumir a API.
            responses = {
                    // Enquanto a propriedade responses (no plural) abre a lista de possibilidades, cada @ApiResponse descreve exatamente uma dessas possibilidades.
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            // Contéudo do Status Code ONDE,
                            // @Content = descreve o conteúdo do body da resposta.
                            // @Schema = define o formato/modelo desse conteúdo
                            //
                            // @implementation aponta para a classe que o Swagger deve usar
                            // para descobrir os atributos do objeto retornado.
                            content = {@Content(schema = @Schema(implementation = PersonDTO.class))
                            }),
                    // Se você deixa vazio (content = @Content), o Swagger entende:
                    // "Apenas o código do status HTTP importa, não espere nenhum JSON/XML de retorno".
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    BookDTO findById(@PathVariable("id") Long id);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""


    @Operation(summary = "Find All Books",
            description = "Finding All Books",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            content = {
                                    @Content(
//                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    List<BookDTO> findAll();

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""



    @Operation(summary = "Adds a new Book",
    description = "Adds a new book by passing in a JSON, XML or YML representation of the person.",
    tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    BookDTO create(@RequestBody
                   BookDTO book);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Updates a book's information",
            description = "Updates a book's information by passing in a JSON, XML or YML representation of the updated person.",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    BookDTO update(@RequestBody BookDTO book);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Deletes a Book",
            description = "Deletes a specific book by their ID",
            tags = {"Book"},
            responses = {
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    ResponseEntity<?> delete(@PathVariable("id") Long id);
}
