package br.com.treinaweb.twprojetos.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import br.com.treinaweb.twprojetos.validators.PessoaValidator;

@ControllerAdvice(assignableTypes = {FuncionarioController.class, ClienteController.class})
public class PessoaController {
    
    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new PessoaValidator());
    }
}
