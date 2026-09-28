package br.com.oopisani.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Serve para avisar o Spring qual código HTTP deve ser enviado caso essa exceção
// Seja disparada e tratada de forma automática (sem um Handler customizado).
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
