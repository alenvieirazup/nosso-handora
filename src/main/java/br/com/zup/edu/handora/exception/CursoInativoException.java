package br.com.zup.edu.handora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CursoInativoException extends RuntimeException {

    public CursoInativoException(String message) {
        super(message);
    }

}
