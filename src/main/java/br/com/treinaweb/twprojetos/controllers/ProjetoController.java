package br.com.treinaweb.twprojetos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.repositories.ProjetoRepository;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoRepository projetoRepository;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/projeto/home");
        modelAndView.addObject("projeto", projetoRepository.findAll());

        return modelAndView;
    }

}
