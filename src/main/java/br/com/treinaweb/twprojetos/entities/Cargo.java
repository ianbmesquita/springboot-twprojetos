package br.com.treinaweb.twprojetos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Cargo extends Entidade {
    
    @NotNull
    @Size(min = 3, max = 80)
    @Column(nullable = false, length = 80, unique = true)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
