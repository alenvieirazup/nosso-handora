package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;

import javax.validation.constraints.NotBlank;

public class NovoCursoRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String descricao;

    public NovoCursoRequest() {
    }

    public NovoCursoRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Curso toModel() {
        return new Curso(nome, descricao);
    }

}