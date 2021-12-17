package br.com.treinaweb.twprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entities.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    
}
