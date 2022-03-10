package br.com.treinaweb.twprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dtos.AlertDTO;
import br.com.treinaweb.twprojetos.entities.Projeto;
import br.com.treinaweb.twprojetos.services.ClienteService;
import br.com.treinaweb.twprojetos.services.FuncionarioService;
import br.com.treinaweb.twprojetos.services.ProjetoService;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("projeto/home");
        modelAndView.addObject("projetos", projetoService.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/detalhes");
        modelAndView.addObject("projeto", projetoService.findById(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");
        modelAndView.addObject("projeto", new Projeto());
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String cadastrar(@Valid Projeto projeto, BindingResult resultado, ModelMap modelMap, 
        RedirectAttributes redirect, @PathVariable(required = false) Long id) {

        Boolean isEdicao = projeto.getId() != null;

        if (resultado.hasErrors()) {
            popularFormulario(modelMap);

            return "projeto/formulario";
        }
       
        if (isEdicao) {
            projetoService.update(projeto, id);
            redirect.addFlashAttribute("alert", new AlertDTO("Projeto editado com sucesso!", "alert-success"));
        } else {
            projetoService.save(projeto);
            redirect.addFlashAttribute("alert", new AlertDTO("Projeto cadastrado com sucesso!", "alert-success"));
        }

        return "redirect:/projetos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");
        modelAndView.addObject("projeto", projetoService.findById(id));
        popularFormulario(modelAndView);

        return modelAndView;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        projetoService.deleteById(id);

        redirect.addFlashAttribute("alert", new AlertDTO("Projeto excluído com sucesso!", "alert-success"));

        return "redirect:/projetos";
    }

    private void popularFormulario(ModelAndView modelAndView) {
        modelAndView.addObject("clientes", clienteService.findAll());
        modelAndView.addObject("lideres", funcionarioService.buscarLideres());
        modelAndView.addObject("funcionarios", funcionarioService.buscarEquipe());
    }

    private void popularFormulario(ModelMap modelMap) {
        modelMap.addAttribute("clientes", clienteService.findAll());
        modelMap.addAttribute("lideres", funcionarioService.buscarLideres());
        modelMap.addAttribute("funcionarios", funcionarioService.buscarEquipe());
    }

}
