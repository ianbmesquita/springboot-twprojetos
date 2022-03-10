package br.com.treinaweb.twprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entities.Projeto;
import br.com.treinaweb.twprojetos.exceptions.ProjetoNaoEncontradoException;
import br.com.treinaweb.twprojetos.repositories.ProjetoRepository;

@Service
public class ProjetoService {
    
    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Projeto> findAll() {
        return projetoRepository.findAll();
    }

    public Projeto findById(Long id) {
        return projetoRepository.findById(id)
            .orElseThrow(() -> new ProjetoNaoEncontradoException(id));
    }

    public Projeto save(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto update(Projeto projeto, Long id) {
        findById(id);

        return projetoRepository.save(projeto);
    }

    public void deleteById(Long id) {
        Projeto projetoEncontrado = findById(id);

        projetoRepository.delete(projetoEncontrado);
    }
}
