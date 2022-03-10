package br.com.treinaweb.twprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR )
public class ClientePossuiProjetosException extends RuntimeException {
    
    public ClientePossuiProjetosException(Long id) {
        super(String.format("O cliente com ID %s possui projeto(s) vinculado(s).", id));
    }
}
