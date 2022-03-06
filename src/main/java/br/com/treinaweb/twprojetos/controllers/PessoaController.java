package br.com.treinaweb.twprojetos.controllers;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.treinaweb.twprojetos.enums.UF;
import br.com.treinaweb.twprojetos.validators.PessoaValidator;

@ControllerAdvice(assignableTypes = {FuncionarioController.class, ClienteController.class})
public class PessoaController {
    
    @InitBinder(value = {"funcionario", "cliente"})
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new PessoaValidator());
    }

    
    @ModelAttribute("ufs")
    public UF[] getUFs() {
        return UF.values();
    }
}
