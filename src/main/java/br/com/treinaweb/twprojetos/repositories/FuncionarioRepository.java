package br.com.treinaweb.twprojetos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.treinaweb.twprojetos.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @EntityGraph(attributePaths = {"endereco", "cargo"})
    List<Funcionario> findAll();

    @Query("select f from Funcionario f where f.cargo.nome = :nomeCargo")
    List<Funcionario> buscarPorCargo(String nomeCargo);

    @Query("select f from Funcionario f where f.cargo.nome <> :nomeCargo")
    List<Funcionario> buscarPorCargoExceto(String nomeCargo);

    // Buscas usando as keywords - O Spring interpreta pelo nome do método (Funcionario -> Cargo -> nome)
    // List<Funcionario> findByCargoNome(String nomeCargo);
    // List<Funcionario> findByCargoNomeNot(String nomeCargo);
    
}
