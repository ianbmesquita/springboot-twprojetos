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

import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.enums.UF;
import br.com.treinaweb.twprojetos.repositories.CargoRepository;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;
import br.com.treinaweb.twprojetos.utils.SenhaUtil;
import br.com.treinaweb.twprojetos.validators.FuncionarioValidator;
import br.com.treinaweb.twprojetos.validators.PessoaValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private CargoRepository cargoRepository;

    @InitBinder("funcionario")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new FuncionarioValidator(funcionarioRepository));
        binder.addValidators(new PessoaValidator());
    }

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/funcionario/home");
        modelAndView.addObject("funcionarios", funcionarioRepository.findAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/funcionario/detalhes");
        modelAndView.addObject("funcionario", funcionarioRepository.getOne(id));

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("/funcionario/formulario");
        modelAndView.addObject("funcionario", new Funcionario());
        modelAndView.addObject("cargos", cargoRepository.findAll());
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap modelMap) {
        if (resultado.hasErrors()) {
            modelMap.addAttribute("cargos", cargoRepository.findAll());
            modelMap.addAttribute("ufs", UF.values());

            return "funcionario/formulario";
        }

        funcionario.setSenha(SenhaUtil.encodePassword(funcionario.getSenha()));
        funcionarioRepository.save(funcionario);

        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/editar")
    public String editar(@Valid Funcionario funcionario, BindingResult resultado, ModelMap modelMap, @PathVariable Long id) {
        if (resultado.hasErrors()) {
            modelMap.addAttribute("cargos", cargoRepository.findAll());
            modelMap.addAttribute("ufs", UF.values());

            return "funcionario/formulario";
        }

        String senhaAtual = funcionarioRepository.getOne(id).getSenha();
        funcionario.setSenha(senhaAtual);

        funcionarioRepository.save(funcionario);

        return "redirect:/funcionarios";
    } 

    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("funcionario/formulario");
        modelAndView.addObject("funcionario", funcionarioRepository.getOne(id));
        modelAndView.addObject("cargos", cargoRepository.findAll());
        modelAndView.addObject("ufs", UF.values());

        return modelAndView;
    }

    @GetMapping("{id}/excluir")
    public String excluir(@PathVariable Long id) {
        funcionarioRepository.deleteById(id);

        return "redirect:/funcionarios";
    }
    
}
