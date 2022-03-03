package br.com.treinaweb.twprojetos.validators;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;

public class FuncionarioValidator implements Validator {

    private FuncionarioRepository funcionarioRepository;

    public FuncionarioValidator(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Funcionario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Funcionario funcionario = (Funcionario) target;

        if (funcionario.getDataAdmissao() != null && funcionario.getDataDemissao() != null) {
            if (funcionario.getDataDemissao().isBefore(funcionario.getDataAdmissao())) {
                errors.rejectValue("dataDemissao", "validacao.funcionario.dataAdmissao.posterior.dataDemissao");
            }
        }

        validaFuncionarioJaCadastradoPorEmail(funcionario, errors);
        validaFuncionarioJaCadastradoPorCPF(funcionario, errors);
    }

    private void validaFuncionarioJaCadastradoPorEmail(Funcionario funcionario, Errors errors) {
        Optional<Funcionario> funcionarioEncontradoPorEmail = funcionarioRepository.findByEmail(funcionario.getEmail());

        if (funcionarioEncontradoPorEmail.isPresent() && !funcionarioEncontradoPorEmail.get().equals(funcionario)) {
            errors.rejectValue("email", "validacao.funcionario.email.existente");
        }
    }

    private void validaFuncionarioJaCadastradoPorCPF(Funcionario funcionario, Errors errors) {
        Optional<Funcionario> funcionarioEncontradoPorCPF = funcionarioRepository.findByCpf(funcionario.getCpf());

        if (funcionarioEncontradoPorCPF.isPresent() && !funcionarioEncontradoPorCPF.get().equals(funcionario)) {
            errors.rejectValue("cpf", "validacao.funcionario.cpf.existente");
        }
    }
    
}
