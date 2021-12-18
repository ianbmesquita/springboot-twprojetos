package br.com.treinaweb.twprojetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @EntityGraph(attributePaths = {"endereco", "cargo"})
    List<Funcionario> findAll();
    
}
