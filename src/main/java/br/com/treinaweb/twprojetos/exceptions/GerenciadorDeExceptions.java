package br.com.treinaweb.twprojetos.exceptions;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GerenciadorDeExceptions implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView("problema");
        modelAndView.addObject("status", status.value());

        switch (status.value()) {
            case 404:
                modelAndView.addObject("titulo", "Página não encontrada.");
                modelAndView.addObject("mensagem", "A página que você procura não existe.");
                modelAndView.addObject("causa", "A url para a página '" + model.get("path") + "' não existe.");
                modelAndView.addObject("cssClass", "text-warning");
                break;
            case 500:
                modelAndView.addObject("titulo", "Erro interno no servidor.");
                modelAndView.addObject("mensagem", "Alguma coisa deu errado.");
                modelAndView.addObject("causa", "Ocorreu um erro inesperado. Tente mais tarde!");
                modelAndView.addObject("cssClass", "text-danger");
                break;
        }

        return modelAndView;
    }
    
}
