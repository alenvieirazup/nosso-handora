package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;

public class CursoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Integer numeroDeVagas;

    public CursoResponse() {}

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.numeroDeVagas = curso.getNumeroDeVagas();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNumeroDeVagas() {
        return numeroDeVagas;
    }

}
