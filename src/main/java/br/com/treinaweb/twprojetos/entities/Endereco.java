package br.com.treinaweb.twprojetos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.treinaweb.twprojetos.enums.UF;

@Entity
public class Endereco extends Entidade {

    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private UF uf;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = true)
    private String complemento;

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        StringBuilder enderecoCompleto = new StringBuilder();
        enderecoCompleto.append(logradouro)
                        .append(", nº ")
                        .append(numero)
                        .append(", ")
                        .append(complemento)
                        .append(" - ")
                        .append(bairro)
                        .append(". ")
                        .append(uf.getDescricao())
                        .append(" - ")
                        .append(cidade)
                        .append(". CEP: ")
                        .append(cep);
                        
        return enderecoCompleto.toString();
    }

}
