package br.com.treinaweb.twprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entities.Funcionario;
import br.com.treinaweb.twprojetos.entities.Projeto;
import br.com.treinaweb.twprojetos.exceptions.FuncionarioNaoEncontradoException;
import br.com.treinaweb.twprojetos.exceptions.FuncionarioLiderDeProjetoException;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;
import br.com.treinaweb.twprojetos.repositories.ProjetoRepository;
import br.com.treinaweb.twprojetos.utils.SenhaUtil;

@Service
public class FuncionarioService {
    
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id)
            .orElseThrow(() -> new FuncionarioNaoEncontradoException(id));
    }

    public Funcionario save(Funcionario funcionario) {
        funcionario.setSenha(SenhaUtil.encodePassword(funcionario.getSenha()));
        
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario update(Funcionario funcionario, Long id) {
        Funcionario funcionarioEncontrado = findById(id);

        funcionario.setSenha(funcionarioEncontrado.getSenha());

        return funcionarioRepository.save(funcionario);
    }

    public void deleteById(Long id) {
        Funcionario funcionarioEncontrado = findById(id);

        if (projetoRepository.findByLider(funcionarioEncontrado).isEmpty()) {
            if (!funcionarioEncontrado.getProjetos().isEmpty()) {
                for (Projeto projeto : funcionarioEncontrado.getProjetos()) {
                    projeto.getEquipe().remove(funcionarioEncontrado);
                    projetoRepository.save(projeto);
                }
            }

            funcionarioRepository.delete(funcionarioEncontrado);
        } else {
            throw new FuncionarioLiderDeProjetoException(id);
        }
    }
}
