package br.com.treinaweb.twprojetos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.repositories.CargoRepository;

@Controller
@RequestMapping("/cargos")
public class CargoController {
    
    @Autowired
    private CargoRepository cargoRepository;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/cargo/home");
        modelAndView.addObject("cargos", cargoRepository.findAll());

        return modelAndView;
    }
}
