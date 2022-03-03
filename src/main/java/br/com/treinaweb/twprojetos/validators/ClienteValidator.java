package br.com.treinaweb.twprojetos.validators;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.treinaweb.twprojetos.entities.Cliente;
import br.com.treinaweb.twprojetos.repositories.ClienteRepository;

public class ClienteValidator implements Validator {

    private ClienteRepository clienteRepository;

    public ClienteValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;    
        
        validaClienteJaCadastradoPorEmail(cliente, errors);
        validaClienteJaCadastradoPorCPF(cliente, errors);
    }
    
    private void validaClienteJaCadastradoPorEmail(Cliente cliente, Errors errors) {
        Optional<Cliente> clienteEncontradoPorEmail = clienteRepository.findByEmail(cliente.getEmail());

        if (clienteEncontradoPorEmail.isPresent() && !clienteEncontradoPorEmail.get().equals(cliente)) {
            errors.rejectValue("email", "validacao.cliente.email.existente");
        }
    }

    private void validaClienteJaCadastradoPorCPF(Cliente cliente, Errors errors) {
        Optional<Cliente> clienteEncontradoPorCPF = clienteRepository.findByCpf(cliente.getCpf());

        if (clienteEncontradoPorCPF.isPresent() && !clienteEncontradoPorCPF.get().equals(cliente)) {
            errors.rejectValue("cpf", "validacao.cliente.cpf.existente");
        }
    }



    
}
