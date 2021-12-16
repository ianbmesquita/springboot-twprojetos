package br.com.treinaweb.twprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}
