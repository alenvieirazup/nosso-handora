package br.com.zup.edu.handora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CursoSemVagaException extends RuntimeException{

    public CursoSemVagaException(String message) {
        super(message);
    }

}
