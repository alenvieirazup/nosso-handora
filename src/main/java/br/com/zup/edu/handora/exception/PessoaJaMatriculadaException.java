package br.com.zup.edu.handora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PessoaJaMatriculadaException extends RuntimeException {

    public PessoaJaMatriculadaException(String message) {
        super(message);
    }

}
