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
import br.com.treinaweb.twprojetos.repositories.ClienteRepository;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;
import br.com.treinaweb.twprojetos.repositories.ProjetoRepository;

@Controller
@RequestMapping("/projetos")
public class ProjetoController {
    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("projeto/home");
        modelAndView.addObject("projetos", projetoRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/detalhes");
        modelAndView.addObject("projeto", projetoRepository.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");
        modelAndView.addObject("projeto", new Projeto());
        modelAndView.addObject("clientes", clienteRepository.findAll());
        modelAndView.addObject("lideres", funcionarioRepository.buscarPorCargo("Gerente"));
        modelAndView.addObject("funcionarios", funcionarioRepository.buscarPorCargoExceto("Gerente"));

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String cadastrar(@Valid Projeto projeto, BindingResult resultado, ModelMap modelMap, RedirectAttributes redirect) {
        Boolean isEdicao = projeto.getId() != null;

        if (resultado.hasErrors()) {
            modelMap.addAttribute("clientes", clienteRepository.findAll());
            modelMap.addAttribute("lideres", funcionarioRepository.buscarPorCargo("Gerente"));
            modelMap.addAttribute("funcionarios", funcionarioRepository.buscarPorCargoExceto("Gerente"));

            return "projeto/formulario";
        }

        projetoRepository.save(projeto);

        if (isEdicao) {
            redirect.addFlashAttribute("alert", new AlertDTO("Projeto editado com sucesso!", "alert-success"));
        } else {
            redirect.addFlashAttribute("alert", new AlertDTO("Projeto cadastrado com sucesso!", "alert-success"));
        }

        return "redirect:/projetos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("projeto/formulario");
        modelAndView.addObject("projeto", projetoRepository.getOne(id));
        modelAndView.addObject("clientes", clienteRepository.findAll());
        modelAndView.addObject("lideres", funcionarioRepository.buscarPorCargo("Gerente"));
        modelAndView.addObject("funcionarios", funcionarioRepository.buscarPorCargoExceto("Gerente"));

        return modelAndView;
    }

    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        projetoRepository.deleteById(id);

        redirect.addFlashAttribute("alert", new AlertDTO("Projeto excluído com sucesso!", "alert-success"));

        return "redirect:/projetos";
    }

}
