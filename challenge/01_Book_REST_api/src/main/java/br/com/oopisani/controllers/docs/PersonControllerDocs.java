package br.com.oopisani.controllers.docs;

import br.com.oopisani.controllers.PersonController;
import br.com.oopisani.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PersonControllerDocs {

    // Criado interface para "limpar" PersonController do @Operation

    // @Operation permite editar a documentação e enriquecer a interface gráfica da nossa API no Swagger UI
    @Operation(summary = "Finds a Person",
            description = "Find a specific person by your ID",
            // Serve para agrupar endpoints específicos. Nesse caso, endpoints do tipo People.
            tags = {"People"},
            // Responses serve para documentar todos os cenários de resposta (HTTP Status Code) que o nosso endpoint
            // pode retornar. Funciona como um aviso antecipado para o dev que consumir a API.
            responses = {
            // Enquanto a propriedade responses (no plural) abre a lista de possibilidades, cada @ApiResponse descreve exatamente uma dessas possibilidades.
                    @ApiResponse(description = "Success",
                            responseCode = "200",
                            // Contéudo do Status Code ONDE,
                            // @Content = diz que a resposta carrega um contéudo no corpo (body) e não apenas o número do status
                            // @Schema = molde desse conteúdo. Aponta para qual classe Java o Swagger deve olhar para descobrir
                            // quais são os atributos.
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
    PersonDTO findById(@PathVariable("id") Long id);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Find All People",
            description = "Finding All People",
            tags = {"People"},
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
    List<PersonDTO> findAll();

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Adds a new Person",
            description = "Adds a new person by passing in a JSON, XML or YML representation of the person.",
            tags = {"People"},
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PersonDTO.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            }
    )
    PersonDTO create(@RequestBody PersonDTO person);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Updates a person's information",
            description = "Updates a person's information by passing in a JSON, XML or YML representation of the updated person.",
            tags = {"People"},
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
    PersonDTO update(@RequestBody PersonDTO person);

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""

    @Operation(summary = "Deletes a Person",
            description = "Deletes a specific person by their ID",
            tags = {"People"},
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

    //"""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
}
