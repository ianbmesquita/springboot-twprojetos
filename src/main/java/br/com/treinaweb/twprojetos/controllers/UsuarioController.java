package br.com.treinaweb.twprojetos.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.twprojetos.dao.AlterarSenhaDAO;
import br.com.treinaweb.twprojetos.dtos.AlertDTO;
import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;
import br.com.treinaweb.twprojetos.utils.SenhaUtil;

@Controller
public class UsuarioController {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/perfil")
    public ModelAndView perfil(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("usuario/perfil");

        Funcionario usuario = funcionarioRepository.findByEmail(principal.getName()).get();
        modelAndView.addObject("usuario", usuario);
        modelAndView.addObject("alterarSenhaForm", new AlterarSenhaDAO());

        return modelAndView;
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(AlterarSenhaDAO form, Principal principal, RedirectAttributes redirect) {
        Funcionario usuario = funcionarioRepository.findByEmail(principal.getName()).get();

        if(SenhaUtil.matches(form.getSenhaAtual(), usuario.getSenha())) {
            usuario.setSenha(SenhaUtil.encodePassword(form.getNovaSenha()));
            funcionarioRepository.save(usuario);
            redirect.addFlashAttribute("alert", new AlertDTO("Senha alterada com sucesso!", "alert-success"));
        } else {
            redirect.addFlashAttribute("alert", new AlertDTO("Senha atual não confere com a cadastrada!", "alert-danger"));
        }

        return "redirect:/perfil";
    }
}
