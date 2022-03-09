package br.com.treinaweb.twprojetos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CargoPossuiFuncionariosException extends RuntimeException {
 
    public CargoPossuiFuncionariosException(Long id) {
        super(String.format("O cargo com ID %s possui funcionário(s) vinculado(s).", id));
    }
}
