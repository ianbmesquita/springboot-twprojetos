package br.com.treinaweb.twprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dtos.AlertDTO;
import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.exceptions.FuncionarioLiderDeProjetoException;
import br.com.treinaweb.twprojetos.services.CargoService;
import br.com.treinaweb.twprojetos.services.FuncionarioService;
import br.com.treinaweb.twprojetos.validators.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioValidator funcionarioValidator;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private CargoService cargoService;

    @InitBinder("funcionario")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(funcionarioValidator);
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/funcionario/home");
        modelAndView.addObject("funcionarios", funcionarioService.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/funcionario/detalhes");
        modelAndView.addObject("funcionario", funcionarioService.findById(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("/funcionario/formulario");
        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoService.findAll());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap modelMap, RedirectAttributes redirect) {
        if (resultado.hasErrors()) {
            modelMap.addAttribute("cargos", cargoService.findAll());

            return "funcionario/formulario";
        }

        funcionarioService.save(funcionario);

        redirect.addFlashAttribute("alert", new AlertDTO("Funcionário(a) cadastrado(a) com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap modelMap, @PathVariable Long id, RedirectAttributes redirect) {
        if (resultado.hasErrors()) {
            modelMap.addAttribute("cargos", cargoService.findAll());

            return "funcionario/formulario";
        }

        funcionarioService.update(funcionario, id);
        redirect.addFlashAttribute("alert", new AlertDTO("Funcionário(a) editado(a) com sucesso!", "alert-success"));

        return "redirect:/funcionarios";
    } 

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");
        modelAndView.addObject("funcionario", funcionarioService.findById(id));
        modelAndView.addObject("cargos", cargoService.findAll());

        return modelAndView;
    }

    @GetMapping("{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            funcionarioService.deleteById(id);
            redirect.addFlashAttribute("alert", new AlertDTO("Funcionário(a) excluído(a) com sucesso!", "alert-success"));
        } catch (FuncionarioLiderDeProjetoException exception) {
            redirect.addFlashAttribute("alert", new AlertDTO("Funcionário(a) não pode excluído(a) pois é líder de projeto(s)!", "alert-danger"));
        }

        return "redirect:/funcionarios";
    }
    
}
