package br.com.zup.edu.handora.controller;

import br.com.zup.edu.handora.model.Curso;

public class CursoResponse {

    private String nome;
    private String descricao;
    private Integer numeroDeVagas;

    public CursoResponse() {}

    public CursoResponse(String nome, String descricao, Integer numeroDeVagas) {
        this.nome = nome;
        this.descricao = descricao;
        this.numeroDeVagas = numeroDeVagas;
    }

    public CursoResponse(Curso curso) {
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.numeroDeVagas = curso.getNumeroDeVagas();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

}
