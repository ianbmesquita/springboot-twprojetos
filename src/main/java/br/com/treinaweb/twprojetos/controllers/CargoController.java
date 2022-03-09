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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dtos.AlertDTO;
import br.com.treinaweb.twprojetos.entities.Cargo;
import br.com.treinaweb.twprojetos.exceptions.CargoPossuiFuncionariosException;
import br.com.treinaweb.twprojetos.services.CargoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/cargo/home");
        modelAndView.addObject("cargos", cargoService.findAll());

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("/cargo/formulario");
        modelAndView.addObject("cargo", new Cargo());

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String cadastrar(@Valid Cargo cargo, BindingResult resposta, RedirectAttributes redirect, @PathVariable(required = false) Long id) {
        Boolean isEdicao = cargo.getId() != null;
        
        if (resposta.hasErrors()) {
            return "cargo/formulario";
        }

        if (isEdicao) {
            cargoService.update(cargo, id);
            redirect.addFlashAttribute("alert", new AlertDTO("Cargo editado com sucesso!", "alert-success"));
        } else {
            cargoService.save(cargo);
            redirect.addFlashAttribute("alert", new AlertDTO("Cargo cadastrado com sucesso!", "alert-success"));
        }

        return "redirect:/cargos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/cargo/formulario");
        modelAndView.addObject("cargo", cargoService.findById(id));

        return modelAndView;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            cargoService.deleteById(id);
            redirect.addFlashAttribute("alert", new AlertDTO("Cargo excluído com sucesso!", "alert-success"));
        } catch (CargoPossuiFuncionariosException exception) {
            redirect.addFlashAttribute("alert", new AlertDTO("Cargo não pode ser excluído pois possui funcionário(s) vinculado(s)", "alert-danger"));
        }

        return "redirect:/cargos";
    }
}
