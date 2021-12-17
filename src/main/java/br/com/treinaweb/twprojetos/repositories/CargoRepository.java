package br.com.treinaweb.twprojetos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twprojetos.entities.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
}
