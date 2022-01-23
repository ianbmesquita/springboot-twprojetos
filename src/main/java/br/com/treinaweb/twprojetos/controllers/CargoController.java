package br.com.treinaweb.twprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.treinaweb.twprojetos.entities.Cargo;
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

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("/cargo/formulario");
        modelAndView.addObject("cargo", new Cargo());

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String cadastrar(@Valid Cargo cargo, BindingResult resposta) {
        if (resposta.hasErrors()) {
            return "cargo/formulario";
        }

        cargoRepository.save(cargo);

        return "redirect:/cargos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/cargo/formulario");
        modelAndView.addObject("cargo", cargoRepository.getOne(id));

        return modelAndView;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        cargoRepository.deleteById(id);

        return "redirect:/cargos";
    }
}
