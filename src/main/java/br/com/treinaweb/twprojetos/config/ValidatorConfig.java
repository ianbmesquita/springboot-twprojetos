package br.com.treinaweb.twprojetos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.treinaweb.twprojetos.repositories.ClienteRepository;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;
import br.com.treinaweb.twprojetos.validators.ClienteValidator;
import br.com.treinaweb.twprojetos.validators.FuncionarioValidator;
import br.com.treinaweb.twprojetos.validators.PessoaValidator;

@Configuration
public class ValidatorConfig {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Bean
    public ClienteValidator clienteValidator() {
        return new ClienteValidator(clienteRepository);
    }

    @Bean
    public FuncionarioValidator funcionarioValidator() {
        return new FuncionarioValidator(funcionarioRepository);
    }

    @Bean
    public PessoaValidator pessoaValidator() {
        return new PessoaValidator();
    }
}
