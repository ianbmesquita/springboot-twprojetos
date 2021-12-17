package br.com.treinaweb.twprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
}
