package br.com.treinaweb.twprojetos.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Funcionario extends Pessoa {

    @NotNull
    @PastOrPresent
    @Column(name = "data_admissao", nullable = false)
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataAdmissao;

    @PastOrPresent
    @Column(name = "data_demissao")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataDemissao;

    @Valid
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    @Size(min = 4, max = 255)
    @Column(nullable = false)
    private String senha;

    @ManyToMany(mappedBy = "equipe")
    private List<Projeto> projetos;

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }
    
}
