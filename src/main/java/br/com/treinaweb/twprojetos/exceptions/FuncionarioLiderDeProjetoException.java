package br.com.treinaweb.twprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class FuncionarioLiderDeProjetoException extends RuntimeException {
    
    public FuncionarioLiderDeProjetoException(Long id) {
        super(String.format("O(A) funcionário(a) com ID %s é lider de projeto(s).", id));
    }
}
