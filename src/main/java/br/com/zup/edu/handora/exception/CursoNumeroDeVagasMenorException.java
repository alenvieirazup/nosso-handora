package br.com.zup.edu.handora.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class CursoNumeroDeVagasMenorException extends RuntimeException {

    public CursoNumeroDeVagasMenorException(String message) {
        super(message);
    }

}
