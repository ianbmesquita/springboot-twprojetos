package br.com.treinaweb.twprojetos.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import br.com.treinaweb.twprojetos.entities.Cliente;
import br.com.treinaweb.twprojetos.repositories.ClienteRepository;
import br.com.treinaweb.twprojetos.validators.ClienteValidator;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @InitBinder("cliente")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new ClienteValidator(clienteRepository));
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("cliente/home");

        modelAndView.addObject("clientes", clienteRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/detalhes");

        modelAndView.addObject("cliente", clienteRepository.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");
        modelAndView.addObject("cliente", new Cliente());

        return modelAndView;
    }

    @PostMapping({"/cadastrar", "/{id}/editar"})
    public String salvar(@Valid Cliente cliente, BindingResult resposta, RedirectAttributes redirect) {
        Boolean isEdicao = cliente.getId() != null;

        if (resposta.hasErrors()) {            
            return "cliente/formulario";
        }

        clienteRepository.save(cliente);

        if (isEdicao) {
            redirect.addFlashAttribute("alert", new AlertDTO("Cliente editado(a) com sucesso!", "alert-success"));
        } else {
            redirect.addFlashAttribute("alert", new AlertDTO("Cliente cadastrado(a) com sucesso!", "alert-success"));
        }

        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("cliente/formulario");
        modelAndView.addObject("cliente", clienteRepository.getOne(id));

        return modelAndView;
    }

    @GetMapping(path="/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        clienteRepository.deleteById(id);

        redirect.addFlashAttribute("alert", new AlertDTO("Cliente excluído(a) com sucesso", "alert-success"));

        return "redirect:/clientes";
    }
}
