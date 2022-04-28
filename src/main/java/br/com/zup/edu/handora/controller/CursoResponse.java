package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;

import javax.validation.constraints.NotBlank;

public class CursoResponse {
    private String nome;
    private String descricao;

    public CursoResponse() {
    }

    public CursoResponse(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public CursoResponse(Curso curso) {
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
