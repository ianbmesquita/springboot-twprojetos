package br.com.treinaweb.twprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entities.Cliente;
import br.com.treinaweb.twprojetos.exceptions.ClienteNaoEncontradoException;
import br.com.treinaweb.twprojetos.exceptions.ClientePossuiProjetosException;
import br.com.treinaweb.twprojetos.repositories.ClienteRepository;
import br.com.treinaweb.twprojetos.repositories.ProjetoRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        Cliente clienteEncontrado = clienteRepository.findById(id)
            .orElseThrow(() -> new ClienteNaoEncontradoException(id));
        
        return clienteEncontrado;
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Cliente cliente, Long id) {
        findById(id);

        return clienteRepository.save(cliente);
    }

    public void deleteById(Long id) {
        Cliente clienteEncontrado = findById(id);

        if (projetoRepository.findByCliente(clienteEncontrado).isEmpty()) {
            clienteRepository.delete(clienteEncontrado);
        } else {
            throw new ClientePossuiProjetosException(id);
        }

    }
}
