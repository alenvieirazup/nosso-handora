package br.com.zup.edu.handora.controller;

import java.time.LocalDateTime;

import br.com.zup.edu.handora.model.Curso;

public class BuscaDinamicaCursoResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Integer numeroDeVagas;
    private LocalDateTime criadoEm;

    public BuscaDinamicaCursoResponse() {}

    public BuscaDinamicaCursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.numeroDeVagas = curso.getNumeroDeVagas();
        this.criadoEm = curso.getCriadoEm();
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

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

}
