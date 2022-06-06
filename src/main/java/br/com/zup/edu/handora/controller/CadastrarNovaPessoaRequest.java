package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Pessoa;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CadastrarNovaPessoaRequest {

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @CPF
    private String cpf;

    public CadastrarNovaPessoaRequest(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public CadastrarNovaPessoaRequest() {
    }

    public Pessoa toModel() {
        return new Pessoa(nome,cpf);
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
