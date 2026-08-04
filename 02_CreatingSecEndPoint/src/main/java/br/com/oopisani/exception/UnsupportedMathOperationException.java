package br.com.oopisani.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Serve para avisar o Spring qual código HTTP deve ser enviado caso essa exceção
// Seja disparada e tratada de forma automática (sem um Handler customizado).
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {
    public UnsupportedMathOperationException(String message) {
        super(message);
    }
}
