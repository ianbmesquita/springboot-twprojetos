package br.com.treinaweb.twprojetos.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Cliente extends Pessoa {
    
    @OneToMany(mappedBy = "cliente")
    private List<Projeto> projetos;

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

}
